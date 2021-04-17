package com.francescsoftware.weathersample.repository.weather

import com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse
import com.francescsoftware.weathersample.type.Result

sealed class WeatherLocation {
    data class City(
        val name: String,
        val countryCode: String,
    ) : WeatherLocation()

    data class Coordinates(
        val latitude: Double,
        val longitude: Double,
    ) : WeatherLocation()
}

interface WeatherRepository {
    suspend fun getTodayWeather(location: WeatherLocation): Result<TodayWeatherResponse>
    suspend fun getForecast(location: WeatherLocation): Result<ForecastResponse>
}
