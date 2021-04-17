package com.francescsoftware.weathersample.repository

import com.francescsoftware.weathersample.type.Result
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
            Result.Success(body)
        } else {
            Result.Failure(IOException("Empty body"))
        }
    } else {
        Result.Failure(IOException("Failed to read response, error code [${response.code()}]"))
    }
} catch (ex: Exception) {
    if (ex is CancellationException) {
        throw ex
    }
    Result.Failure(ex)
}
