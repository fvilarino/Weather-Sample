package com.francescsoftware.weathersample.feature.weather.tabs.today

import com.francescsoftware.weathersample.feature.common.mvi.Event
import com.francescsoftware.weathersample.feature.common.mvi.MviIntent
import com.francescsoftware.weathersample.feature.common.mvi.ReduceAction
import com.francescsoftware.weathersample.feature.common.mvi.State

data class TodayState(
    val currentTemperature: CharSequence,
    val minTemperature: CharSequence,
    val maxTemperature: CharSequence,
): State {
    companion object {
        val initial = TodayState(
            currentTemperature = "",
            minTemperature = "",
            maxTemperature = "",
        )
    }
}

sealed class TodayEvent : Event

sealed class TodayMviIntent : MviIntent {
    data class Load(
        val city: String,
        val countryCode: String,
    ): TodayMviIntent()
}

sealed class TodayReduceAction : ReduceAction {
    data class Loaded(
        val currentTemperature: CharSequence,
        val minTemperature: CharSequence,
        val maxTemperature: CharSequence,
    ) : TodayReduceAction()
}
