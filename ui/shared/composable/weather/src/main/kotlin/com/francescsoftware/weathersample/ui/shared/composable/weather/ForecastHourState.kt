package com.francescsoftware.weathersample.ui.shared.composable.weather

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex

/**
 * The weather forecast state
 *
 * @property id a unique ID for this forecast
 * @property time the forecast time
 * @property description the forecast description
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
    val time: String,
    val description: String,
    @DrawableRes val iconId: Int,
    val temperature: Temperature,
    val feelsLikeTemperature: Temperature,
    val precipitation: Precipitation,
    val uvIndex: UvIndex,
    val windSpeed: Speed,
    val humidity: Humidity,
    val visibility: AverageVisibility,
)
