package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.Serializable

@Serializable
data class Page(
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val number: Int
)
