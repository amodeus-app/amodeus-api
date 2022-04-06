package me.evgfilim1.amodeus.api.upstream.models

sealed interface PaginatedResponse {
    val page: Page
}
