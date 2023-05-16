package com.francescsoftware.weathersample.data.repository.city.api

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse

/** Repository for cities */
interface CityRepository {
    /**
     * Gets a list of cities matching the [prefix]
     *
     * @param prefix - prefix to filter cities by
     * @param limit - maximum number of results to return
     * @return an [Either] with a [CitySearchResponse]
     */
    suspend fun getCities(
        prefix: String = "",
        limit: Int = 10,
    ): Either<CitySearchResponse>
}
