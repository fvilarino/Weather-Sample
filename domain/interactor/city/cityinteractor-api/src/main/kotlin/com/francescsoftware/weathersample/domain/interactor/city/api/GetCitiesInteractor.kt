package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Cities

/** Gets a list of cities matching the prefix */
interface GetCitiesInteractor {
    /**
     * Gets a list of cities matching the [prefix]
     *
     * @param prefix - prefix to filter cities
     * @param limit - max number of results to return
     * @return an [Either] with the resulting [Cities]
     */
    suspend operator fun invoke(prefix: String = "", limit: Int = 10): Either<Cities>
}
