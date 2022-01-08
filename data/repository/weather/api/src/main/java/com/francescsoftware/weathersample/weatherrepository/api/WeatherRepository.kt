package com.francescsoftware.weathersample.weatherrepository.api

import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.weatherrepository.api.model.today.TodayWeatherResponse

sealed interface WeatherLocation {
    data class City(
        val name: String,
        val countryCode: String,
    ) : WeatherLocation

    data class Coordinates(
        val latitude: Double,
        val longitude: Double,
    ) : WeatherLocation
}

interface WeatherRepository {
    suspend fun getTodayWeather(location: WeatherLocation): Result<TodayWeatherResponse>
    suspend fun getForecast(location: WeatherLocation): Result<ForecastResponse>
}
