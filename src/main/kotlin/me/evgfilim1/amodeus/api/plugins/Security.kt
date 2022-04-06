package me.evgfilim1.amodeus.api.plugins

import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.evgfilim1.amodeus.api.models.APIError
import me.evgfilim1.amodeus.api.models.Auth
import me.evgfilim1.amodeus.api.upstream.Modeus

data class UserTokenPrincipal(val token: String, val refreshToken: String?) : Principal

class OAuth2PasswordBearerAuthenticationProvider private constructor(config: Configuration) :
    AuthenticationProvider(config) {
    val authenticationFunction = config.authenticationFunction

    class Configuration(name: String?) : AuthenticationProvider.Configuration(name) {
        var authenticationFunction: AuthenticationFunction<String> = {
            UserTokenPrincipal(it, null)
        }

        fun validate(body: AuthenticationFunction<String>) {
            authenticationFunction = body
        }

        fun build() = OAuth2PasswordBearerAuthenticationProvider(this)
    }
}

fun Authentication.Configuration.oauth2Password(
    name: String? = null,
    configure: OAuth2PasswordBearerAuthenticationProvider.Configuration.() -> Unit,
) {
    val provider =
        OAuth2PasswordBearerAuthenticationProvider.Configuration(name).apply(configure).build()

    provider.pipeline.intercept(AuthenticationPipeline.RequestAuthentication) { ctx ->
        val authHeader = call.request.parseAuthorizationHeader()
        val principal = if (authHeader is HttpAuthHeader.Single)
            provider.authenticationFunction(call, authHeader.blob)
        else
            null
        if (principal == null) {
            val cause = if (authHeader == null)
                AuthenticationFailedCause.NoCredentials
            else
                AuthenticationFailedCause.InvalidCredentials
            ctx.challenge("OAuth2Password", cause) {
                call.respond(HttpStatusCode.Unauthorized,
                    mapOf("detail" to "Missing or invalid credentials"))
                if (!it.completed && call.response.status() != null) it.complete()
            }
        } else {
            ctx.principal(principal)
        }
    }

    register(provider)
}

fun Application.configureSecurity() {
    install(Authentication) {
        oauth2Password { }
    }

    routing {
        post("auth") {
            val params = call.receiveOrNull<Parameters>() ?: return@post call.respond(
                HttpStatusCode.UnprocessableEntity,
                APIError("parameters are missing"),
            )
            val username = params["username"] ?: return@post call.respond(
                HttpStatusCode.UnprocessableEntity,
                APIError("username is missing"),
            )
            val password = params["password"] ?: return@post call.respond(
                HttpStatusCode.UnprocessableEntity,
                APIError("password is missing"),
            )
            call.respond(Auth(
                Modeus.authenticate(username, password),
                3600,
            ))
        }

        authenticate {
            get("me") {
                call.respond("that's you!")
            }
        }
    }
}
