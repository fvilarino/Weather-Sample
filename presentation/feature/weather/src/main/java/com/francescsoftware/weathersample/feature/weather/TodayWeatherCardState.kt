package com.francescsoftware.weathersample.feature.weather

import androidx.annotation.DrawableRes

internal data class TodayWeatherCardState(
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
