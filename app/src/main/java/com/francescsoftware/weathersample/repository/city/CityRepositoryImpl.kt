package com.francescsoftware.weathersample.repository.city

import com.francescsoftware.weathersample.repository.city.model.CitySearchResponse
import com.francescsoftware.weathersample.repository.safeApiCall
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cityService: CityService
) : CityRepository {

    override suspend fun getCities(
        prefix: String,
        limit: Int,
    ): Result<CitySearchResponse> = safeApiCall {
       cityService.getCities(prefix, limit)
   }
}
