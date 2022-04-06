package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.putJsonArray
import me.evgfilim1.amodeus.api.utils.json

@Serializable
class SearchPeopleResponse(
    override val _embedded: JsonObject = emptyEmbedded,
    override val page: Page,
): ResultResponse<SearchPeopleResult>, PaginatedResponse {
    override val result: SearchPeopleResult by lazy {
        json.decodeFromJsonElement(_embedded)
    }

    companion object {
        private val emptyEmbedded = buildJsonObject { putJsonArray("persons") {} }
    }
}
