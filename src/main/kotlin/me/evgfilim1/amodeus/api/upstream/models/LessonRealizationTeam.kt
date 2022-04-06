package me.evgfilim1.amodeus.api.upstream.models

import me.evgfilim1.amodeus.api.utils.UUID

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class LessonRealizationTeam(
    override val id: UUID,
    val name: String,
    override val _links: JsonObject,
) : IDModeusModel
