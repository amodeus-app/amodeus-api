package me.evgfilim1.amodeus.api.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.evgfilim1.amodeus.api.utils.UUID

@Serializable
data class TimetableElement(
    val id: UUID,
    val lesson: Lesson,
    val start: Instant,
    val end: Instant,
    val location: Location?,
    val teachers: List<Person>,
    val team_name: String?,
)
