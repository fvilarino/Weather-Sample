package com.francescsoftware.weathersample.presentation.feature.weather.tabs.today

import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import com.francescsoftware.weathersample.presentation.shared.mvi.Event
import com.francescsoftware.weathersample.presentation.shared.mvi.MviIntent
import com.francescsoftware.weathersample.presentation.shared.mvi.ReduceAction
import com.francescsoftware.weathersample.presentation.shared.mvi.State

enum class TodayLoadState {
    IDLE,
    LOADING,
    LOADED,
    ERROR
}

data class TodayState(
    val loadState: TodayLoadState,
    val todayState: TodayWeatherCardState,
    val errorMessage: CharSequence,
) : State {
    val loading: Boolean = loadState == TodayLoadState.LOADING
    val loaded: Boolean = loadState == TodayLoadState.LOADED
    val loadError: Boolean = loadState == TodayLoadState.ERROR

    companion object {
        val initial = TodayState(
            loadState = TodayLoadState.IDLE,
            todayState = TodayWeatherCardState(),
            errorMessage = "",
        )
    }
}

sealed class TodayEvent : Event

sealed class TodayMviIntent : MviIntent {
    data class Load(val selectedCity: SelectedCity) : TodayMviIntent()
    object Retry : TodayMviIntent()
}

sealed class TodayReduceAction : ReduceAction {
    object Loading : TodayReduceAction()
    data class Loaded(
        val currentTemperature: CharSequence,
        val minTemperature: CharSequence,
        val maxTemperature: CharSequence,
        val feelsLikeTemperature: CharSequence,
        val description: CharSequence,
        val windSpeed: CharSequence,
        val humidity: CharSequence,
        val pressure: CharSequence,
        val visibility: CharSequence,
        val iconId: Int,
    ) : TodayReduceAction()

    data class LoadError(val message: CharSequence) : TodayReduceAction()
}
