package com.francescsoftware.weathersample.cityrepository.impl

import com.francescsoftware.weathersample.cityrepository.api.CityRepository
import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse
import com.francescsoftware.weathersample.network.safeApiCall
import com.francescsoftware.weathersample.type.Either
import javax.inject.Inject

internal class CityRepositoryImpl @Inject constructor(
    private val cityService: CityService
) : CityRepository {

    override suspend fun getCities(
        prefix: String,
        limit: Int,
    ): Either<CitySearchResponse> = safeApiCall {
        cityService.getCities(prefix, limit)
    }
}
