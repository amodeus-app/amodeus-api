package me.evgfilim1.amodeus.api.upstream

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import it.skrape.core.htmlDocument
import kotlinx.datetime.Instant
import kotlinx.serialization.json.*
import me.evgfilim1.amodeus.api.mappers.EventTeamMapper
import me.evgfilim1.amodeus.api.mappers.EventsMapper
import me.evgfilim1.amodeus.api.mappers.PeopleMapper
import me.evgfilim1.amodeus.api.models.Person
import me.evgfilim1.amodeus.api.models.TimetableElement
import me.evgfilim1.amodeus.api.upstream.models.*
import me.evgfilim1.amodeus.api.upstream.models.input.EventsSearch
import me.evgfilim1.amodeus.api.upstream.models.input.PeopleSearch
import me.evgfilim1.amodeus.api.utils.UUID
import me.evgfilim1.amodeus.api.utils.json
import kotlin.random.Random

class Modeus(credentials: String) {
    private val client = HttpClient {
        defaultRequest {
            url("https://utmn.modeus.org/")
            bearerAuth(credentials)
            accept(ContentType.Application.HalJson)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) { json(json) }
        install(HttpTimeout) {
            connectTimeoutMillis = 5000
            socketTimeoutMillis = 10000
        }
    }

    private suspend fun request(method: HttpMethod, path: String): JsonObject =
        client.request(path) {
            this.method = method
        }.body()

    private suspend inline fun <reified T> request(method: HttpMethod, path: String, body: T): JsonObject =
        client.request(path) {
            this.method = method
            setBody(body)
        }.body()

    class TimetableClient internal constructor(private val client: Modeus) {
        private suspend fun request(method: HttpMethod, path: String): JsonObject =
            client.request(method, "schedule-calendar-v2/api/$path")

        private suspend inline fun <reified T> request(method: HttpMethod, path: String, body: T): JsonObject =
            client.request(method, "schedule-calendar-v2/api/$path", body)

        suspend fun getTimetable(personUUID: UUID, start: Instant, end: Instant, size: Int = 500): List<TimetableElement> {
            val obj = request(
                HttpMethod.Post,
                "calendar/events/search",
                EventsSearch(
                    attendees = listOf(personUUID),
                    size = size,
                    timeMin = start,
                    timeMax = end,
                ),
            )
            return EventsMapper.mapMany(json.decodeFromJsonElement<SearchEventsResponse>(obj))
        }

        suspend fun getEvent(eventUUID: UUID): TimetableElement? {
            val obj = request(HttpMethod.Get, "calendar/events/${eventUUID.value}")
            return EventsMapper.mapOne(
                json.decodeFromJsonElement(obj),
                json.decodeFromJsonElement<SearchEventResponse>(obj),
            )
        }

        suspend fun getEventTeam(eventUUID: UUID): List<Person> {
            val obj = request(HttpMethod.Get, "calendar/events/${eventUUID.value}/team")
            return EventTeamMapper.mapMany(json.decodeFromJsonElement<GetEventTeamResponse>(obj))
        }

        suspend fun searchPeople(q: String, page: Int = 0, size: Int = 25): List<Person> {
            val obj = request(
                HttpMethod.Post,
                "people/persons/search",
                PeopleSearch(
                    fullName = q,
                    page = page,
                    size = size,
                    sort = "+fullName",
                ),
            )
            return PeopleMapper.mapMany(json.decodeFromJsonElement<SearchPeopleResponse>(obj))
        }

        suspend fun getPerson(personUUID: UUID): Person? {
            val obj = request(
                HttpMethod.Post,
                "people/persons/search",
                PeopleSearch(
                    id = listOf(personUUID),
                    page = 0,
                    size = 25,
                    sort = "+fullName",
                )
            )
            return PeopleMapper.mapMany(json.decodeFromJsonElement<SearchPeopleResponse>(obj)).firstOrNull()
        }
    }

    val timetableClient: TimetableClient
        get() = TimetableClient(this)

    companion object {
        suspend fun authenticate(login: String, password: String): String {
            val client = HttpClient {
                defaultRequest {
                    url("https://utmn.modeus.org/")
                    accept(ContentType.Application.HalJson)
                    accept(ContentType.Application.Json)
                    accept(ContentType.Text.Html)
                    accept(ContentType.Any)
                    header("Accept-Language", "ru,en-US,en;q=0.5")
                }
                install(ContentNegotiation) { json(json) }
                install(HttpCookies) { storage = AcceptAllCookiesStorage() }

                followRedirects = false
                expectSuccess = false
            }

            val data = client
                .get("/schedule-calendar/assets/app.config.json")
                .body<JsonObject>()["legacy"]!!
                .jsonObject["appConfig"]!!
                .jsonObject["httpAuth"]!!
                .jsonObject
            val authUrl = data["authUrl"]!!.jsonPrimitive.content
            val clientId = data["clientId"]!!.jsonPrimitive.content

            val nonce = Random.nextBytes(16).joinToString("") { "%02x".format(it) }
            val state = Random.nextBytes(16).joinToString("") { "%02x".format(it) }
            val loginUrl = client.get(authUrl) {
                parameter("client_id", clientId)
                parameter("redirect_uri", "https://utmn.modeus.org/")
                parameter("response_type", "id_token")
                parameter("scope", "openid")
                parameter("nonce", nonce)
                parameter("state", state)
            }.headers["Location"]!!

            val loginPageResponse = client.submitForm(
                loginUrl,
                Parameters.build {
                    append("UserName", login)
                    append("Password", password)
                    append("AuthMethod", "FormsAuthentication")
                },
            )
            val redirectUrl = loginPageResponse.headers["Location"]
            if (redirectUrl == null) {
                val errorText = htmlDocument(loginPageResponse.bodyAsText()) {
                    findFirst("#errorText") { text }
                }
                throw RuntimeException("error: $errorText")
            }

            val (commonAuthAction, commonAuthData) = htmlDocument(client.get(redirectUrl) {
                expectSuccess = true
            }.bodyAsText()) {
                findFirst("form") { attribute("action") } to Parameters.build {
                    findAll("form input[type=\"hidden\"]") {
                        for (el in this) {
                            append(el.attribute("name"), el.attribute("value"))
                        }
                    }
                }
            }
            val urlWithToken = client.head(
                client.submitForm(commonAuthAction, commonAuthData).headers["Location"]!!
            ).headers["Location"]!!
            return Regex("id_token=([a-zA-Z0-9\\-_.]+)").find(urlWithToken)!!.groups[1]!!.value
        }
    }
}
