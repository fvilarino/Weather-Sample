package com.francescsoftware.weathersample.repository.city

import com.francescsoftware.weathersample.repository.city.model.CitySearchResponse
import com.francescsoftware.weathersample.type.Result

interface CityRepository {
    suspend fun getCities(
        prefix: String = "",
        limit: Int = 10
    ): Result<CitySearchResponse>
}
