package com.francescsoftware.weathersample.shared.composable.weather

/**
 * Forecast header
 *
 * @property id a unique ID
 * @property date the forecast date
 * @property sunrise the sunrise time
 * @property sunset the sunset time
 */
data class ForecastHeaderState(
    val id: String,
    val date: String,
    val sunrise: String,
    val sunset: String,
)
