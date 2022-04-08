package me.evgfilim1.amodeus.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Auth(
    val access_token: String,
    val expires_in: Long,
    val refresh_token: String? = null,
    val token_type: String = "Bearer",
    val person: Person,
)
