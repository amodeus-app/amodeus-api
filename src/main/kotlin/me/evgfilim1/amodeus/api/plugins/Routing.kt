package me.evgfilim1.amodeus.api.plugins

import io.ktor.client.network.sockets.*
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.evgfilim1.amodeus.api.models.APIError
import me.evgfilim1.amodeus.api.upstream.Modeus
import me.evgfilim1.amodeus.api.utils.UUID
import me.evgfilim1.amodeus.api.utils.swaggerUIHTML
import java.io.File

@Serializable
@Resource("/people/search")
class SearchPeople(val q: String, val page: Int = 0, val limit: Int = 25)

@Serializable
@Resource("/people/{id}")
class Person(val id: UUID) {
    @Serializable
    @Resource("timetable")
    class Timetable(val parent: Person, val from: Instant, val to: Instant)
}

@Serializable
@Resource("/events/{id}")
class Event(val id: UUID) {
    @Serializable
    @Resource("team")
    class Team(val parent: Event)
}

fun Application.configureRouting() {
    install(Resources)
    install(StatusPages) {
        exception<ConnectTimeoutException> { call, cause ->
            call.respond(HttpStatusCode.GatewayTimeout, APIError("Timed out waiting for Modeus to respond"))
            log.warn("Timed out waiting for Modeus to respond", cause)
        }
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, APIError(cause.message ?: "Internal server error"))
            log.error("Internal server error", cause)
        }
    }

    routing {
        get("/") {
            call.respondText(swaggerUIHTML, ContentType.Text.Html)
        }
        get("/openapi.yaml") {
            call.respondFile(File("openapi.yaml"))
        }
        authenticate {
            get<SearchPeople> {
                val client = Modeus(
                    call.principal<UserTokenPrincipal>()!!.token
                ).timetableClient
                call.respond(client.searchPeople(it.q, it.page, it.limit))
            }
            get<Person> {
                val client = Modeus(
                    call.principal<UserTokenPrincipal>()!!.token
                ).timetableClient
                client.getPerson(it.id)
                    ?.let { p -> call.respond(p) }
                    ?: call.respond(HttpStatusCode.NotFound, APIError("Person not found"))
            }
            get<Person.Timetable> {
                val client = Modeus(
                    call.principal<UserTokenPrincipal>()!!.token
                ).timetableClient
                call.respond(client.getTimetable(it.parent.id, it.from, it.to))
            }
            get<Event.Team> {
                val client = Modeus(
                    call.principal<UserTokenPrincipal>()!!.token
                ).timetableClient
                call.respond(client.getEventTeam(it.parent.id))
            }
        }
    }
}
