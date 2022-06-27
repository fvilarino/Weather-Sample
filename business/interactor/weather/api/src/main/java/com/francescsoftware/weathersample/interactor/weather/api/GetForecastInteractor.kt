package com.francescsoftware.weathersample.interactor.weather.api

import com.francescsoftware.weathersample.type.Result
import java.util.Date

data class Forecast(
    val items: List<ForecastDay>,
)

data class ForecastDay(
    val date: Date,
    val sunrise: Date,
    val sunset: Date,
    val entries: List<ForecastEntry>,
)

data class ForecastEntry(
    val date: Date,
    val description: String,
    val icon: String,
    val minTemperature: Double,
    val maxTemperature: Double,
    val feelsLikeTemperature: Double,
    val windSpeed: Double,
    val humidityPercent: Int,
    val visibility: Int,
)

interface GetForecastInteractor {
    suspend fun execute(location: WeatherLocation): Result<Forecast>
}
