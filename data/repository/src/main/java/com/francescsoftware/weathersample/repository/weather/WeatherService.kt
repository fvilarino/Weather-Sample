package com.francescsoftware.weathersample.repository.weather

import com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getTodayWeather(
        @Query("q") cityAndCountry: String? = null,
        @Query("lat") latitude: Double? = null,
        @Query("lon") longitude: Double? = null,
        @Query("units") units: String = "metric",
    ): Response<TodayWeatherResponse>

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") cityAndCountry: String? = null,
        @Query("lat") latitude: Double? = null,
        @Query("lon") longitude: Double? = null,
        @Query("units") units: String = "metric",
    ): Response<ForecastResponse>
}
