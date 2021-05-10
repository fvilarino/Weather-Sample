package com.francescsoftware.weathersample.storage.selectedcity

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.francescsoftware.weathersample.presentation.storage.SelectedCity
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SelectedCitySerializer : Serializer<SelectedCity> {

    override val defaultValue: SelectedCity = SelectedCity.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SelectedCity = try {
        SelectedCity.parseDelimitedFrom(input)
    } catch (ex: InvalidProtocolBufferException) {
        throw CorruptionException("Cannot read proto buff", ex)
    }

    override suspend fun writeTo(t: SelectedCity, output: OutputStream) =
        t.writeDelimitedTo(output)
}
