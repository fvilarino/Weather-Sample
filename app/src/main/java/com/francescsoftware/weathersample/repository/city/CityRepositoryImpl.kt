package com.francescsoftware.weathersample.repository.city

import com.francescsoftware.weathersample.repository.city.model.CitySearchResponse
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class CityRepositoryImpl @Inject constructor(
    private val cityService: CityService
) : CityRepository {

    override suspend fun getCities(
        prefix: String,
        limit: Int,
    ): Result<CitySearchResponse> = safeApiCall {
       cityService.getCities(prefix, limit)
   }
}

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
