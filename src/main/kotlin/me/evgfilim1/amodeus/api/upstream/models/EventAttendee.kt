package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import me.evgfilim1.amodeus.api.utils.UUID

@Serializable
data class EventAttendee(
    override val id: UUID,
    override val _links: JsonObject,
) : IDModeusModel
