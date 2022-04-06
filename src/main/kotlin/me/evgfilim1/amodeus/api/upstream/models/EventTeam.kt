package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.SerialName
import me.evgfilim1.amodeus.api.utils.UUID

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class EventTeam(
    @SerialName("eventId") val eventID: UUID,
    val size: Int,
    override val _links: JsonObject,
) : ModeusModel
