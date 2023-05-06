package com.francescsoftware.weathersample.interactor.city.api

import com.francescsoftware.weathersample.interactor.city.api.model.Cities
import com.francescsoftware.weathersample.type.Either

/** Gets a list of cities matching the prefix */
interface GetCitiesInteractor {
    /**
     * Gets a list of cities matching the [prefix]
     *
     * @param prefix - prefix to filter cities
     * @param limit - max number of results to return
     * @return an [Either] with a [List] of [City]
     */
    suspend fun execute(prefix: String = "", limit: Int = 10): Either<Cities>
}
