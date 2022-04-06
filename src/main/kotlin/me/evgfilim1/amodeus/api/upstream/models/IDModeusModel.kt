package me.evgfilim1.amodeus.api.upstream.models

import me.evgfilim1.amodeus.api.utils.UUID

sealed interface IDModeusModel: ModeusModel {
    val id: UUID
}
