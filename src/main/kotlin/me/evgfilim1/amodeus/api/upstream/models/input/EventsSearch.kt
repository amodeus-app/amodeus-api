package me.evgfilim1.amodeus.api.upstream.models.input

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.evgfilim1.amodeus.api.utils.UUID

@Serializable
data class EventsSearch(
    @SerialName("attendeePersonId") val attendees: List<UUID>,
    val size: Int,
    val timeMin: Instant,
    val timeMax: Instant,
)
