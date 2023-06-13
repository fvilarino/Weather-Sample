package com.francescsoftware.weathersample.data.repository.weather.api.model.forecast

/**
 * Weather forecast for the day
 *
 * @property date forecast date
 * @property dateEpoch forecast date in seconds since Unix epoch
 * @property day day conditions
 * @property hour list of hourly conditions
 * @property astro astronomical data for the day
 */
data class ForecastDay(
    val date: String,
    val dateEpoch: Int,
    val day: Day,
    val hour: List<ForecastHour>,
    val astro: Astro,
)
