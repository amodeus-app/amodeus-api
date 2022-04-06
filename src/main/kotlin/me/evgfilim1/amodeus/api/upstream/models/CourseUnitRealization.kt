package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import me.evgfilim1.amodeus.api.utils.UUID

@Serializable
data class CourseUnitRealization(
    override val id: UUID,
    val name: String,
    val nameShort: String,
    override val _links: JsonObject,
) : IDModeusModel
