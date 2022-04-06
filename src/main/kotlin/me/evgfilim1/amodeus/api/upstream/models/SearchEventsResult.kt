package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchEventsResult(
    val events: List<Event> = emptyList(),
    @SerialName("course-unit-realizations") val courseUnitRealizations: List<CourseUnitRealization>,
    @SerialName("cycle-realizations") val cycleRealizations: List<CycleRealization>,
    @SerialName("lesson-realization-teams") val lessonRealizationTeams: List<LessonRealizationTeam>,
    @SerialName("lesson-realizations") val lessonRealizations: List<LessonRealization>,
    @SerialName("event-locations") val eventLocations: List<EventLocation>,
    @SerialName("event-rooms") val eventRooms: List<EventRoom>,
    val rooms: List<Room>,
    val buildings: List<Building>,
    @SerialName("event-teams") val eventTeams: List<EventTeam>,
    @SerialName("event-organizers") val eventOrganizers: List<EventOrganizer>,
    @SerialName("event-attendances") val eventAttendances: List<EventAttendance>,
    @SerialName("event-attendees") val eventAttendees: List<EventAttendee>,
    val persons: List<Person>,
)
