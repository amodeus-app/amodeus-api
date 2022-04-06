package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.Serializable

@Serializable
data class SearchPeopleResult(
    val persons: List<Person>,
)
