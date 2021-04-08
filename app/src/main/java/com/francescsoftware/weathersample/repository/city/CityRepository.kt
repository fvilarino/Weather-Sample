package com.francescsoftware.weathersample.repository.city

import com.francescsoftware.weathersample.repository.city.model.CitySearchResponse

interface CityRepository {
    suspend fun getCities(
        prefix: String = "",
        limit: Int = 10
    ) : Result<CitySearchResponse>
}
