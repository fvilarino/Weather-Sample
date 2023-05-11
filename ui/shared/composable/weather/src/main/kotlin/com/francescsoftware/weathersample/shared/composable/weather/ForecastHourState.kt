package com.francescsoftware.weathersample.shared.composable.weather

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

/**
 * The weather forecast state
 *
 * @property id a unique ID for this forecast
 * @property header the forecast header
 * @property iconId the icon associated with the forecast
 * @property temperature the temperature in celsius
 * @property feelsLikeTemperature the feels like temperature in celsius
 * @property precipitation the precipitation in mm
 * @property uvIndex the UV index
 * @property windSpeed the wind speed in km/h
 * @property humidity the humidity
 * @property visibility the visibility in km
 */
@Immutable
data class ForecastHourState(
    val id: Long,
    val header: String,
    @DrawableRes val iconId: Int,
    val temperature: String,
    val feelsLikeTemperature: String,
    val precipitation: String,
    val uvIndex: String,
    val windSpeed: String,
    val humidity: String,
    val visibility: String,
)
