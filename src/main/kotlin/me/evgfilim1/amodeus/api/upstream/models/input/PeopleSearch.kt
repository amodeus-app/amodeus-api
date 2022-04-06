package me.evgfilim1.amodeus.api.upstream.models.input

import kotlinx.serialization.Serializable
import me.evgfilim1.amodeus.api.utils.UUID

@Serializable
data class PeopleSearch(
    val id: List<UUID>? = null,
    val fullName: String? = null,
    val page: Int,
    val size: Int,
    val sort: String,
) {
    init {
        require(id != null || fullName != null) { "id or fullName must be set" }
        require(id == null || fullName == null) { "id and fullName cannot be set at the same time" }
    }
}
