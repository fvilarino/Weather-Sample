package com.francescsoftware.weathersample.domain.interactor.weather.api.model

import java.util.Date

/**
 * Weather forecast for a day
 *
 * @property date - the date of each forecast
 * @property sunrise - the sunrise time
 * @property sunset - the sunset time
 * @property entries - a [List] of [ForecastEntry] for this day
 */
data class ForecastDay(
    val date: Date,
    val sunrise: String,
    val sunset: String,
    val entries: List<ForecastEntry>,
)
