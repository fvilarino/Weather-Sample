package com.francescsoftware.weathersample.weatherrepository.impl

import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.weatherrepository.api.model.today.TodayWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherService {
    @GET("current.json")
    suspend fun getTodayWeather(
        @Query("q") query: String? = null,
    ): Response<TodayWeatherResponse>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") query: String? = null,
        @Query("days") days: Int = 7,
    ): Response<ForecastResponse>
}
