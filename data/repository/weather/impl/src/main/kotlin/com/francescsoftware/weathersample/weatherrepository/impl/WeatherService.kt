package com.francescsoftware.weathersample.weatherrepository.impl

import com.francescsoftware.weathersample.weatherrepository.impl.model.forecast.ForecastModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.today.TodayWeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherService {
    @GET("current.json")
    suspend fun getTodayWeather(
        @Query("q") query: String? = null,
    ): Response<TodayWeatherModel>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") query: String? = null,
        @Query("days") days: Int = 3,
    ): Response<ForecastModel>
}
