package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.json.JsonObject

sealed interface ResultResponse<T> {
    val _embedded: JsonObject
    val result: T
}
