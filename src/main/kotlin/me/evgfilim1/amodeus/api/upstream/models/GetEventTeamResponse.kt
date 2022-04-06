package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import me.evgfilim1.amodeus.api.utils.UUID
import me.evgfilim1.amodeus.api.utils.json

@Serializable
data class GetEventTeamResponse(
    override val _embedded: JsonObject,
    @SerialName("eventId") val eventID: UUID,
    val size: Int,
): ResultResponse<GetEventTeamResult> {
    override val result: GetEventTeamResult by lazy {
        json.decodeFromJsonElement(_embedded)
    }
}
