package com.francescsoftware.weathersample.data.repository.city.impl

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.network.safeApiCall
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.data.repository.city.api.CitiesException
import com.francescsoftware.weathersample.data.repository.city.api.CityRepository
import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class CityRepositoryImpl @Inject constructor(
    private val cityService: CityService,
    private val dispatcherProvider: DispatcherProvider,
) : CityRepository {

    override suspend fun getCities(
        prefix: String,
        limit: Int,
    ): Either<CitySearchResponse> {
        val networkResponse = safeApiCall {
            cityService.getCities(prefix, limit)
        }
        return networkResponse.fold(
            onSuccess = { response ->
                if (response.isValid) {
                    withContext(dispatcherProvider.default) {
                        Either.Success(response.toCityResponse())
                    }
                } else {
                    Either.Failure(
                        CitiesException("Invalid data received")
                    )
                }
            },
            onFailure = { throwable ->
                Either.Failure(CitiesException(cause = throwable))
            }
        )
    }
}
