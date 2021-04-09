package com.francescsoftware.weathersample.repository

import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T : Any> safeApiCall(
    block: suspend () -> Response<T>
): Result<T> = try {
    val response = block()
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            Result.success(body)
        } else {
            Result.failure(IOException("Empty body"))
        }
    } else {
        Result.failure(IOException("Failed to read response, error code [${response.code()}]"))
    }
} catch (ex: Exception) {
    if (ex is CancellationException) {
        throw ex
    }
    Result.failure(ex)
}
