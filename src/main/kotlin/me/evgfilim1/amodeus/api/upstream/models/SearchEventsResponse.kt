package me.evgfilim1.amodeus.api.upstream.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import me.evgfilim1.amodeus.api.utils.json

@Serializable
class SearchEventsResponse(
    override val _embedded: JsonObject,
    override val page: Page,
) : ResultResponse<SearchEventsResult>, PaginatedResponse {
    override val result: SearchEventsResult by lazy {
        json.decodeFromJsonElement(_embedded)
    }
}
