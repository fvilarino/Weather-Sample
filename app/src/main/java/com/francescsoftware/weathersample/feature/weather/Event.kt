package com.francescsoftware.weathersample.feature.weather

import com.francescsoftware.weathersample.feature.common.mvi.Event
import com.francescsoftware.weathersample.feature.common.mvi.MviIntent
import com.francescsoftware.weathersample.feature.common.mvi.ReduceAction
import com.francescsoftware.weathersample.feature.common.mvi.State

data class WeatherState(
    val cityName: CharSequence,
) : State {
    companion object {
        val initial = WeatherState(
            cityName = "",
        )
    }
}

sealed class WeatherEvent : Event

sealed class WeatherIntent : MviIntent {
    data class CityParsed(val city: SelectedCity) : WeatherIntent()
}

sealed class WeatherReduceAction : ReduceAction {
    data class UpdateCity(val city: CharSequence) : WeatherReduceAction()
}
