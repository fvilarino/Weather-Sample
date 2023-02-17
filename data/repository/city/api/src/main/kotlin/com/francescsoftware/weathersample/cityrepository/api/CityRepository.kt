package com.francescsoftware.weathersample.cityrepository.api

import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse
import com.francescsoftware.weathersample.type.Either

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
