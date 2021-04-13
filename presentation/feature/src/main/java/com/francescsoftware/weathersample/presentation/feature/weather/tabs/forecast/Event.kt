package com.francescsoftware.weathersample.presentation.feature.weather.tabs.forecast

import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import com.francescsoftware.weathersample.presentation.shared.mvi.Event
import com.francescsoftware.weathersample.presentation.shared.mvi.MviIntent
import com.francescsoftware.weathersample.presentation.shared.mvi.ReduceAction
import com.francescsoftware.weathersample.presentation.shared.mvi.State
import com.francescsoftware.weathersample.presentation.shared.recyclerview.RecyclerViewBindingItem

enum class ForecastLoadState {
    IDLE,
    LOADING,
    LOADED,
    ERROR
}

data class ForecastState(
    val loadState: ForecastLoadState,
    val forecast: List<RecyclerViewBindingItem>,
    val errorMessage: CharSequence,
) : State {
    val loading: Boolean = loadState == ForecastLoadState.LOADING
    val loaded: Boolean = loadState == ForecastLoadState.LOADED
    val loadError: Boolean = loadState == ForecastLoadState.ERROR

    companion object {
        val initial = ForecastState(
            loadState = ForecastLoadState.IDLE,
            forecast = emptyList(),
            errorMessage = "",
        )
    }
}

sealed class ForecastEvent : Event

sealed class ForecastMviIntent : MviIntent {
    data class Load(val selectedCity: SelectedCity) : ForecastMviIntent()
    object Retry : ForecastMviIntent()
}

sealed class ForecastReduceAction : ReduceAction {
    object Loading : ForecastReduceAction()
    data class Loaded(val items: List<RecyclerViewBindingItem>) : ForecastReduceAction()
    data class LoadError(val message: CharSequence) : ForecastReduceAction()
}
