package com.francescsoftware.weathersample.domain.interactor.weather.api.model

import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex

/**
 * Current weather conditions
 *
 * @property description the current weather description
 * @property code the code for the weather icon
 * @property temperature the temperature in Celsius
 * @property feelsLike the feels like temperature in Celsius
 * @property wind the wind speed
 * @property gust the gusts speed
 * @property humidity the humidity percentage
 * @property pressure the atmospheric pressure in millibars
 * @property precipitation the precipitation in mm
 * @property uvIndex the UV index
 * @property visibility the average visibility
 * @property iconCode the icon ID for the weather conditions
 */
data class Current(
    val description: String,
    val code: Int,
    val temperature: Temperature,
    val feelsLike: Temperature,
    val wind: Speed,
    val gust: Speed,
    val humidity: Humidity,
    val pressure: Pressure,
    val precipitation: Precipitation,
    val uvIndex: UvIndex,
    val visibility: AverageVisibility,
    val iconCode: Int,
)
