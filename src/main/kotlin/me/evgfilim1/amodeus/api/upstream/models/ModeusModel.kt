package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

sealed interface ModeusModel {
    val _links: JsonObject

    fun getLink(rel: String): String? {
        return _links.jsonObject[rel]?.jsonObject?.get("href")?.jsonPrimitive?.content?.replaceFirst("/", "")
    }
}
