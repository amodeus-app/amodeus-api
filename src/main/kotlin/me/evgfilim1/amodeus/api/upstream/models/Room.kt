package me.evgfilim1.amodeus.api.upstream.models

import me.evgfilim1.amodeus.api.utils.UUID

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class Room(
    override val id: UUID,
    val name: String,
    val nameShort: String,
    val building: Building,
    val projectorAvailable: Boolean,
    val totalCapacity: Int,
    val workingCapacity: Int,
    override val _links: JsonObject,
) : IDModeusModel
