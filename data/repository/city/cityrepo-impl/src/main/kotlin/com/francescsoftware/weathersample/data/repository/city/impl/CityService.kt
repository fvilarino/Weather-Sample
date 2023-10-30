package com.francescsoftware.weathersample.data.repository.city.impl

import com.francescsoftware.weathersample.data.repository.city.impl.model.CitySearchResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {
    @GET("v1/geo/cities")
    suspend fun getCities(
        @Query("namePrefix") namePrefix: String?,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?,
    ): Response<CitySearchResponseModel>
}
