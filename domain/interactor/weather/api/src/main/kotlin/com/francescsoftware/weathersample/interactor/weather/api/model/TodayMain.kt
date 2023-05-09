package com.francescsoftware.weathersample.interactor.weather.api.model

/**
 * Main weather attributes
 *
 * @property description - precipitation in mm
 * @property code - weather code
 * @property temp - temperature
 * @property feelsLike - feels like temperature
 * @property humidity - humidity percent
 * @property pressure - atmospheric pressure
 * @property precipitation - precipitation in mm
 * @property uvIndex - UV index
 */
data class TodayMain(
    val description: String,
    val code: Int,
    val temp: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val precipitation: Int,
    val uvIndex: Int,
)
