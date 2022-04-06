package me.evgfilim1.amodeus.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    val subject: Subject,
    val name: String,
    val name_short: String,
    val description: String?,
    val type: String,
    val format: String?,
)
