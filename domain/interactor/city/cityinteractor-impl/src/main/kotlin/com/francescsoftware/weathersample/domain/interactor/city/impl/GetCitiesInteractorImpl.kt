package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.data.repository.city.api.CityRepository
import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse
import com.francescsoftware.weathersample.domain.interactor.city.api.CitiesException
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Cities
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class GetCitiesInteractorImpl @Inject constructor(
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
                    ),
                )
            },
        )
    }
}
