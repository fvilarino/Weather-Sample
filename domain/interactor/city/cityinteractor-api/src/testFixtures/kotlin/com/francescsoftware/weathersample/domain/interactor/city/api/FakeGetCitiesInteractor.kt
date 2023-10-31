package com.francescsoftware.weathersample.domain.interactor.city.api

import app.cash.turbine.Turbine
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Cities
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Metadata
import java.io.IOException

class FakeGetCitiesInteractor : GetCitiesInteractor {
    val citiesResult = Turbine<List<City>?>()

    override suspend fun invoke(params: GetCitiesInteractor.Params): Either<Cities> {
        val cities = citiesResult.awaitItem()
        return if (cities == null) {
            Either.Failure(IOException("Failed to load cities"))
        } else {
            Either.Success(
                Cities(
                    metadata = Metadata(
                        offset = 0,
                        totalCount = cities.size,
                    ),
                    cities = cities,
                ),
            )
        }
    }
}
