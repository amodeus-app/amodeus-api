package me.evgfilim1.amodeus.api.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(me.evgfilim1.amodeus.api.utils.json)
    }
}
