package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetEventTeamResult(
    @SerialName("event-attendees") val eventAttendees: List<EventAttendee>,
    val persons: List<Person>,
)
