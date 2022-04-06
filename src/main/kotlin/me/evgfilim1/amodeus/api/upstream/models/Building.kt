package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.Serializable

@Serializable
data class Building(
    val name: String,
    val nameShort: String,
    val address: String,
    val displayOrder: Int,
)
