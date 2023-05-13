package com.francescsoftware.weathersample.interactor.weather.api.model

import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex

/**
 * Main weather attributes
 *
 * @property description - precipitation in mm
 * @property code - weather code
 * @property temperature - temperature
 * @property feelsLike - feels like temperature
 * @property humidity - humidity percent
 * @property pressure - atmospheric pressure
 * @property precipitation - precipitation in mm
 * @property uvIndex - UV index
 */
data class TodayMain(
    val description: String,
    val code: Int,
    val temperature: Temperature,
    val feelsLike: Temperature,
    val humidity: Humidity,
    val pressure: Pressure,
    val precipitation: Precipitation,
    val uvIndex: UvIndex,
)
