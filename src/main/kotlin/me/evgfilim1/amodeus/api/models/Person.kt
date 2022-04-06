package me.evgfilim1.amodeus.api.models

import kotlinx.serialization.Serializable
import me.evgfilim1.amodeus.api.utils.UUID

@Serializable
data class Person(
    val id: UUID,
    val last_name: String,
    val first_name: String,
    val middle_name: String?,
    val full_name: String,
)
