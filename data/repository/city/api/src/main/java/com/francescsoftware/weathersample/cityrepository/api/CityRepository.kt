package com.francescsoftware.weathersample.cityrepository.api

import com.francescsoftware.weathersample.type.Result
 import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse

interface CityRepository {
    suspend fun getCities(
        prefix: String = "",
        limit: Int = 10,
    ): Result<CitySearchResponse>
}
