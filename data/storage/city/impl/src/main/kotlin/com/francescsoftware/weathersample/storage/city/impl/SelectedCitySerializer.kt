package com.francescsoftware.weathersample.storage.city.impl

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/** Serializes a [SelectedCity] */
object SelectedCitySerializer : Serializer<SelectedCity> {

    /** @{inheritDoc} */
    override val defaultValue: SelectedCity = SelectedCity.getDefaultInstance()

    /** @{inheritDoc} */
    override suspend fun readFrom(input: InputStream): SelectedCity = try {
        SelectedCity.parseDelimitedFrom(input)
    } catch (ex: InvalidProtocolBufferException) {
        throw CorruptionException("Cannot read proto buff", ex)
    }

    /** @{inheritDoc} */
    override suspend fun writeTo(t: SelectedCity, output: OutputStream) =
        t.writeDelimitedTo(output)
}
