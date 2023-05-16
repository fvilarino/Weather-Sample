package com.francescsoftware.weathersample.shared.composable.weather

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

/**
 * Current weather state
 *
 * @property temperature the current temperature in celsius
 * @property feelsLikeTemperature the feels like temperature in celsius
 * @property precipitation the precipitation in mm
 * @property uvIndex the UV index
 * @property description the weather description
 * @property iconId the icon associated with the weather
 * @property windSpeed the wind speed in km/h
 * @property humidity the humidity
 * @property pressure the pressure in mmbar
 * @property visibility the visibility in km
 */
@Immutable
data class CurrentWeatherState(
    val temperature: String = "",
    val feelsLikeTemperature: String = "",
    val precipitation: String = "",
    val uvIndex: String = "",
    val description: String = "",
    @DrawableRes val iconId: Int = 0,
    val windSpeed: String = "",
    val humidity: String = "",
    val pressure: String = "",
    val visibility: String = "",
)
