package me.evgfilim1.amodeus.api.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@JvmInline
@Serializable(with = UUID.Companion::class)
value class UUID(val value: String) {
    companion object : KSerializer<UUID> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): UUID {
            val uuid = decoder.decodeString()
            if (!uuid.matches(Regex("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")))
                throw IllegalArgumentException("$uuid is not a valid UUID")
            return UUID(uuid)
        }

        override fun serialize(encoder: Encoder, value: UUID) {
            encoder.encodeString(value.value)
        }
    }
}
