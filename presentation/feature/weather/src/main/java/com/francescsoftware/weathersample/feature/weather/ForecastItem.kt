package com.francescsoftware.weathersample.feature.weather

import androidx.annotation.DrawableRes

sealed interface ForecastItem {
    data class ForecastHeader(
        val id: Long,
        val date: String,
        val sunrise: String,
        val sunset: String,
    ) : ForecastItem

    data class ForecastCard(
        val id: Long,
        val header: String,
        @DrawableRes val iconId: Int,
        val minTemperature: String,
        val maxTemperature: String,
        val feelsLikeTemperature: String,
        val windSpeed: String,
        val humidity: String,
        val visibility: String,
    ) : ForecastItem
}
