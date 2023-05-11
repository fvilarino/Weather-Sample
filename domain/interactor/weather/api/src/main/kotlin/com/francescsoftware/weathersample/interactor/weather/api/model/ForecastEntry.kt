package com.francescsoftware.weathersample.interactor.weather.api.model

import java.util.Date

/**
 * A weather forecast
 *
 * @property date - the [Date] this forecast is for
 * @property description - forecast description
 * @property iconCode - the code for the icon representing this forecast
 * @property temperature - the temperature
 * @property feelsLikeTemperature - the feels like temperature
 * @property precipitation - the amount of precipitation
 * @property windSpeed - the wind speed
 * @property uvIndex - the UV index
 * @property humidityPercent - the humidity percent
 * @property visibility - the visibility in meters
 */
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
