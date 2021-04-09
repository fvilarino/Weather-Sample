package com.francescsoftware.weathersample.presentation.feature.weather

import com.francescsoftware.weathersample.presentation.shared.mvi.Event
import com.francescsoftware.weathersample.presentation.shared.mvi.MviIntent
import com.francescsoftware.weathersample.presentation.shared.mvi.ReduceAction
import com.francescsoftware.weathersample.presentation.shared.mvi.State

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
