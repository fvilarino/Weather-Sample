package com.francescsoftware.weathersample.interactor.city.impl

import com.francescsoftware.weathersample.cityrepository.api.CityRepository
import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.dispather.DispatcherProvider
import com.francescsoftware.weathersample.interactor.city.api.CitiesException
import com.francescsoftware.weathersample.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.interactor.city.api.model.Cities
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetCitiesInteractorImpl @Inject constructor(
    private val cityRepository: CityRepository,
    private val dispatcherProvider: DispatcherProvider,
) : GetCitiesInteractor {

    override suspend operator fun invoke(prefix: String, limit: Int): Either<Cities> {
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
