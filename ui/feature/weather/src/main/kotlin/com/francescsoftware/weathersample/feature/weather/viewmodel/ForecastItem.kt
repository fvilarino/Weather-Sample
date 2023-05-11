package com.francescsoftware.weathersample.feature.weather.viewmodel

import com.francescsoftware.weathersample.shared.composable.weather.ForecastHourState
import kotlinx.collections.immutable.ImmutableList

internal data class ForecastDayState(
    val header: ForecastHeaderState,
    val forecast: ImmutableList<ForecastHourState>,
)

internal data class ForecastHeaderState(
    val id: String,
    val date: String,
    val sunrise: String,
    val sunset: String,
)
