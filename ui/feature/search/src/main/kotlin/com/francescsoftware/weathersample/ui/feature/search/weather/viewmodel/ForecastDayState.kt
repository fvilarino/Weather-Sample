package com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel

import androidx.compose.runtime.Immutable
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class ForecastDayState(
    val header: ForecastHeaderState,
    val forecast: ImmutableList<ForecastHourState>,
)
