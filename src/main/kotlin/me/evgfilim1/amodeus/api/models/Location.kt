package me.evgfilim1.amodeus.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val building: Building?,
    val room: String?,
    val full: String,
)
