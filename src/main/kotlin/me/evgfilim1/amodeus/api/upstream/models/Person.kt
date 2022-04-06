package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import me.evgfilim1.amodeus.api.utils.UUID

@Serializable
data class Person(
    override val id: UUID,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val fullName: String,
    override val _links: JsonObject,
) : IDModeusModel
