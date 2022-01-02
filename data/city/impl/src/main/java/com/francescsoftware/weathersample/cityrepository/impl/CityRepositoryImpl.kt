package com.francescsoftware.weathersample.cityrepository.impl

import com.francescsoftware.weathersample.network.safeApiCall
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersaple.cityrepository.api.CityRepository
import com.francescsoftware.weathersaple.cityrepository.api.model.CitySearchResponse
import javax.inject.Inject

internal class CityRepositoryImpl @Inject constructor(
    private val cityService: CityService
) : CityRepository {

    override suspend fun getCities(
        prefix: String,
        limit: Int,
    ): Result<CitySearchResponse> = safeApiCall {
        cityService.getCities(prefix, limit)
    }
}
