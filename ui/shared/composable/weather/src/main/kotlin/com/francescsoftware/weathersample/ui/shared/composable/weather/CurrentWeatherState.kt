package com.francescsoftware.weathersample.ui.shared.composable.weather

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex

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
 * @property pressure the pressure in millibars
 * @property visibility the visibility in km
 */
@Immutable
data class CurrentWeatherState(
    val temperature: Temperature = Temperature.fromCelsius(0.0),
    val feelsLikeTemperature: Temperature = Temperature.fromCelsius(0.0),
    val precipitation: Precipitation = Precipitation.fromMillimeters(0.0),
    val uvIndex: UvIndex = UvIndex(0),
    val description: String = "",
    @DrawableRes val iconId: Int = 0,
    val windSpeed: Speed = Speed.fromKph(0.0),
    val humidity: Humidity = Humidity(0),
    val pressure: Pressure = Pressure.fromMillibars(0.0),
    val visibility: AverageVisibility = AverageVisibility.fromKm(0.0),
)
