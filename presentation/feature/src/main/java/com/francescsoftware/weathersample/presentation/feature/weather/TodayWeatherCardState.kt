package com.francescsoftware.weathersample.presentation.feature.weather

import androidx.annotation.DrawableRes

data class TodayWeatherCardState(
    val temperature: String = "",
    val minTemperature: String = "",
    val maxTemperature: String = "",
    val feelsLikeTemperature: String = "",
    val description: String = "",
    @DrawableRes val iconId: Int = 0,
    val windSpeed: String = "",
    val humidity: String = "",
    val pressure: String = "",
    val visibility: String = "",
)
