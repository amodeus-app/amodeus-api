package me.evgfilim1.amodeus.api.mappers

import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import me.evgfilim1.amodeus.api.upstream.models.ModeusModel
import me.evgfilim1.amodeus.api.upstream.models.ResultResponse
import me.evgfilim1.amodeus.api.utils.json

interface Mapper<T, RT, MT> {
    fun mapOne(obj: T, res: ResultResponse<RT>): MT?
    fun mapMany(res: ResultResponse<RT>): List<MT>
}
