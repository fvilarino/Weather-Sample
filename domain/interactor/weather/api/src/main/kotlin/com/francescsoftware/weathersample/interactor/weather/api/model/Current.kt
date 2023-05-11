package com.francescsoftware.weathersample.interactor.weather.api.model

/**
 * Current weather conditions
 *
 * @property description the current weather description
 * @property code the code for the weather icon
 * @property temperature the temperature in Celsius
 * @property feelsLike the feels like temperature in Celsius
 * @property humidity the humidity percentage
 * @property pressure the atmospheric pressure in millibars
 * @property precipitation the precipitation in mm
 */
data class Current(
    val description: String,
    val code: Int,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Double,
    val precipitation: Double,
)
