package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.data.repository.city.api.CityRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.CitiesException
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Cities
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.francescsoftware.weathersample.data.repository.city.api.CitiesException as RepoCitiesException

@ContributesBinding(AppScope::class)
class GetCitiesInteractorImpl @Inject constructor(
    private val cityRepository: CityRepository,
    private val dispatcherProvider: DispatcherProvider,
) : GetCitiesInteractor {

    override suspend fun invoke(params: GetCitiesInteractor.Params): Either<Cities> = try {
        val cities = cityRepository.getCitiesByPrefix(
            prefix = params.prefix,
            offset = 0,
            limit = params.limit,
        )
        withContext(dispatcherProvider.default) {
            Either.Success(cities.toCities())
        }
    } catch (ex: RepoCitiesException) {
        Either.Failure(
            CitiesException(
                message = ex.message ?: "Error fetching cities",
                cause = ex.cause,
            ),
        )
    }
}
