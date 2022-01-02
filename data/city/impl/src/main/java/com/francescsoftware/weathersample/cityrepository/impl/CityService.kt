package com.francescsoftware.weathersample.cityrepository.impl

import com.francescsoftware.weathersaple.cityrepository.api.model.CitySearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CityService {
    @GET("v1/geo/cities")
    suspend fun getCities(
        @Query("namePrefix") namePrefix: String?,
        @Query("limit") limit: Int?,
    ): Response<CitySearchResponse>
}
