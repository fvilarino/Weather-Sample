package com.francescsoftware.weathersample.domain.interactor.weather.api.model

/**
 * Weather forecast for multiple days
 *
 * @property current the current weather conditions
 * @property items a [List] of [ForecastDay]
 */
data class Forecast(
    val current: Current,
    val items: List<ForecastDay>,
)
