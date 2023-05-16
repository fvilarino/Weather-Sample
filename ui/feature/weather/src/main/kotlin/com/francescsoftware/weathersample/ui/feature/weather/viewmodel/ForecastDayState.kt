package com.francescsoftware.weathersample.ui.feature.weather.viewmodel

import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import kotlinx.collections.immutable.ImmutableList

internal data class ForecastDayState(
    val header: ForecastHeaderState,
    val forecast: ImmutableList<ForecastHourState>,
)
