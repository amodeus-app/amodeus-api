package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import me.evgfilim1.amodeus.api.utils.UUID

@Serializable
data class Event(
    override val id: UUID,
    val name: String,
    val nameShort: String,
    val description: String?,
    val start: Instant,
    val end: Instant,
    override val _links: JsonObject,
) : IDModeusModel
