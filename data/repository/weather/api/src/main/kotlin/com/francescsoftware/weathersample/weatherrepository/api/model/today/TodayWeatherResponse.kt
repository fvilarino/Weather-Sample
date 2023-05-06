package com.francescsoftware.weathersample.weatherrepository.api.model.today

import com.francescsoftware.weathersample.weatherrepository.api.model.Current
import com.francescsoftware.weathersample.weatherrepository.api.model.Location

/**
 * Current weather conditions
 *
 * @property current weather conditions
 * @property location weather location
 */
data class TodayWeatherResponse(
    val current: Current,
    val location: Location,
)
