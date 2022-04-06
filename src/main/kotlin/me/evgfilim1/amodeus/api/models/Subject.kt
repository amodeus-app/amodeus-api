package me.evgfilim1.amodeus.api.models

import kotlinx.serialization.Serializable
import me.evgfilim1.amodeus.api.utils.UUID

@Serializable
data class Subject(
    val id: UUID,
    val name: String,
    val name_short: String,
)
