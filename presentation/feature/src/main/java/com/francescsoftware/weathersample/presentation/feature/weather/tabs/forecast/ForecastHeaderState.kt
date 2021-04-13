package com.francescsoftware.weathersample.presentation.feature.weather.tabs.forecast

import com.francescsoftware.weathersample.presentation.shared.recyclerview.Diffable

data class ForecastHeaderState(
    override val id: Long,
    val date: CharSequence,
    val sunrise: CharSequence,
    val sunset: CharSequence,
) : Diffable
