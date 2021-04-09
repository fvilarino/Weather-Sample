package com.francescsoftware.weathersample.repository.city

import com.francescsoftware.weathersample.repository.city.model.CitySearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {
    @GET("v1/geo/cities")
    suspend fun getCities(
        @Query("namePrefix") namePrefix: String?,
        @Query("limit") limit: Int?,
    ): Response<CitySearchResponse>
}
