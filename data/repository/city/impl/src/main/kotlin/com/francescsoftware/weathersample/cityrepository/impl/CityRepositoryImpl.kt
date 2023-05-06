package com.francescsoftware.weathersample.cityrepository.impl

import com.francescsoftware.weathersample.cityrepository.api.CitiesException
import com.francescsoftware.weathersample.cityrepository.api.CityRepository
import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse
import com.francescsoftware.weathersample.dispather.DispatcherProvider
import com.francescsoftware.weathersample.network.safeApiCall
import com.francescsoftware.weathersample.type.Either
import com.francescsoftware.weathersample.type.fold
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
