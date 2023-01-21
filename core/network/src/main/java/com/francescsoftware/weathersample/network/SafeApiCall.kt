package com.francescsoftware.weathersample.network

import com.francescsoftware.weathersample.type.Either
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T : Any> safeApiCall(
    block: suspend () -> retrofit2.Response<T>
): Either<T> = try {
    val response = block()
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            Either.Success(body)
        } else {
            Either.Failure(IOException("Empty body"))
        }
    } else {
        Either.Failure(IOException("Failed to read response, error code [${response.code()}]"))
    }
} catch (ex: Exception) {
    if (ex is CancellationException) {
        throw ex
    }
    Either.Failure(ex)
}
