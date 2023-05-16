package com.francescsoftware.weathersample.domain.interactor.weather.api.model

import com.francescsoftware.weathersample.core.type.weather.Speed

/**
 * Wind
 *
 * @property direction - wind direction
 * @property speed - wind speed
 * @property gust - gust speed
 */
data class TodayWind(
    val direction: String,
    val speed: Speed,
    val gust: Speed,
)
