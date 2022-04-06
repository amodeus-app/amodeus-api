package me.evgfilim1.amodeus.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Building(
    val number: Int,
    val address: String,
)
