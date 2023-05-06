package com.francescsoftware.weathersample.interactor.city.impl

import com.francescsoftware.weathersample.cityrepository.api.CityRepository
import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse
import com.francescsoftware.weathersample.dispather.DispatcherProvider
import com.francescsoftware.weathersample.interactor.city.api.CitiesException
import com.francescsoftware.weathersample.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.interactor.city.api.model.Cities
import com.francescsoftware.weathersample.type.Either
import com.francescsoftware.weathersample.type.fold
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetCitiesInteractorImpl @Inject constructor(
    private val cityRepository: CityRepository,
    private val dispatcherProvider: DispatcherProvider,
) : GetCitiesInteractor {

    override suspend fun execute(prefix: String, limit: Int): Either<Cities> {
        val citiesResponse: Either<CitySearchResponse> = cityRepository.getCities(prefix, limit)
        return citiesResponse.fold(
            onSuccess = { response ->
                withContext(dispatcherProvider.default) {
                    Either.Success(response.toCities())
                }
            },
            onFailure = { response ->
                Either.Failure(
                    CitiesException(
                        message = response.message ?: "Error fetching cities",
                        cause = response.cause,
                    )
                )
            }
        )
    }
}
