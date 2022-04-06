package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import me.evgfilim1.amodeus.api.utils.UUID

@Serializable
data class EventAttendance(
    override val id: UUID,
    @SerialName("resultId") val resultID: String,
    override val _links: JsonObject,
) : IDModeusModel
