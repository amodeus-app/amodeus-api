package me.evgfilim1.amodeus.api

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.evgfilim1.amodeus.api.plugins.*

suspend fun main() {
    embeddedServer(Netty, port = 8000, host = "0.0.0.0") {
        configureSerialization()
        configureSecurity()
        configureHTTP()
        configureRouting()
        configureMonitoring()
    }.start(wait = true)
}
