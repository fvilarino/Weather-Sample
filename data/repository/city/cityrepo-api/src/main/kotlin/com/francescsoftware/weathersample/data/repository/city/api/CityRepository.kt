package com.francescsoftware.weathersample.data.repository.city.api

import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse

/** Repository for cities */
interface CityRepository {
    /**
     * Gets a list of cities matching the [prefix]
     *
     * @param prefix prefix to filter cities by
     * @param limit maximum number of results to return
     * @param offset page offset
     * @return a [CitySearchResponse]
     */
    suspend fun getCities(
        prefix: String = "",
        offset: Int = 0,
        limit: Int = 10,
    ): CitySearchResponse
}
