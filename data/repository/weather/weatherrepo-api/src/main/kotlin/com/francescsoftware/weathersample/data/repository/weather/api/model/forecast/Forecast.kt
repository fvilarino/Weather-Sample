package com.francescsoftware.weathersample.data.repository.weather.api.model.forecast

/**
 * Weather forecast information
 *
 * @property forecastDay a list of [ForecastDay] with daily forecast data
 */
data class Forecast(
    val forecastDay: List<ForecastDay>,
)
