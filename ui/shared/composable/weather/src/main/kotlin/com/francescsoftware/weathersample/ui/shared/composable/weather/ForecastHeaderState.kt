package com.francescsoftware.weathersample.ui.shared.composable.weather

/** Forecast header types */
sealed interface ForecastDate {
    /** Today header */
    object Today : ForecastDate

    /** Tomorrow header */
    object Tomorrow : ForecastDate

    /**
     * Forecast header other than today or tomorrow
     *
     * @property date the date for the forecast
     */
    data class Day(val date: String) : ForecastDate
}

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
    val date: ForecastDate,
    val sunrise: String,
    val sunset: String,
)
