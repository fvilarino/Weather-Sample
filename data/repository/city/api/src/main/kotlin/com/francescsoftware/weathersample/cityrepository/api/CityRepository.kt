package com.francescsoftware.weathersample.cityrepository.api

import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse
import com.francescsoftware.weathersample.type.Either

interface CityRepository {
    suspend fun getCities(
        prefix: String = "",
        limit: Int = 10,
    ): Either<CitySearchResponse>
}
