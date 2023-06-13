package com.francescsoftware.weathersample.data.repository.weather.api.model.forecast

import com.francescsoftware.weathersample.data.repository.weather.api.model.Current
import com.francescsoftware.weathersample.data.repository.weather.api.model.Location

/**
 * Weather forecast
 *
 * @property current current weather conditions
 * @property location location for the forecast
 * @property forecast weather forecast
 */
data class ForecastResponse(
    val current: Current,
    val location: Location,
    val forecast: Forecast,
)
