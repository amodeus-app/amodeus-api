package me.evgfilim1.amodeus.api.models

import kotlinx.serialization.Serializable

@Serializable
data class APIError(
    val detail: String,
)
