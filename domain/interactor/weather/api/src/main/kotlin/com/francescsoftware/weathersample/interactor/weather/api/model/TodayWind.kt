package com.francescsoftware.weathersample.interactor.weather.api.model

/**
 * Wind
 *
 * @property direction - wind direction
 * @property speed - wind speed
 * @property gust - gust speed
 */
data class TodayWind(
    val direction: String,
    val speed: Double,
    val gust: Double,
)
