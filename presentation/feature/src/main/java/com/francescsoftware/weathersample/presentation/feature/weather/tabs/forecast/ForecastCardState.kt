package com.francescsoftware.weathersample.presentation.feature.weather.tabs.forecast

import androidx.annotation.DrawableRes
import com.francescsoftware.weathersample.presentation.shared.recyclerview.Diffable

data class ForecastCardState(
    override val id: Long,
    val header: CharSequence,
    @DrawableRes val iconId: Int,
    val minTemperature: CharSequence,
    val maxTemperature: CharSequence,
    val feelsLikeTemperature: CharSequence,
    val windSpeed: CharSequence,
    val humidity: CharSequence,
    val visibility: CharSequence,
) : Diffable
