package com.francescsoftware.weathersample.feature.weather.viewmodel

import androidx.annotation.DrawableRes

data class ForecastDayState(
    val header: ForecastHeaderState,
    val forecast: List<ForecastHourState>,
)

data class ForecastHeaderState(
    val id: String,
    val date: String,
    val sunrise: String,
    val sunset: String,
)

data class ForecastHourState(
    val id: Long,
    val header: String,
    @DrawableRes val iconId: Int,
    val temperature: String,
    val feelsLikeTemperature: String,
    val precipitation: String,
    val uvIndex: String,
    val windSpeed: String,
    val humidity: String,
    val visibility: String,
)