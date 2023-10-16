package com.francescsoftware.data.persistence.settings.impl

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object AppSettingsSerializer : Serializer<AppPreferences> {

    override val defaultValue: AppPreferences = AppPreferences.getDefaultInstance().toBuilder()
        .setAppTheme(AppPreferences.Theme.SYSTEM)
        .setDynamicColors(true)
        .build()

    override suspend fun readFrom(input: InputStream): AppPreferences = try {
        AppPreferences.parseFrom(input)
    } catch (ex: InvalidProtocolBufferException) {
        throw CorruptionException("Cannot read proto buff", ex)
    }

    override suspend fun writeTo(t: AppPreferences, output: OutputStream) =
        t.writeTo(output)
}
