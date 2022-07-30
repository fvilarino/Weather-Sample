package com.francescsoftware.weathersample.interactor.weather.api

import com.francescsoftware.weathersample.type.Result
import java.util.Date

data class Forecast(
    val items: List<ForecastDay>,
)

data class ForecastDay(
    val date: Date,
    val sunrise: String,
    val sunset: String,
    val entries: List<ForecastEntry>,
)

data class ForecastEntry(
    val date: Date,
    val description: String,
    val iconCode: Int,
    val temperature: Double,
    val feelsLikeTemperature: Double,
    val precipitation: Int,
    val windSpeed: Double,
    val uvIndex: Int,
    val humidityPercent: Int,
    val visibility: Int,
)

interface GetForecastInteractor {
    suspend fun execute(location: WeatherLocation): Result<Forecast>
}
