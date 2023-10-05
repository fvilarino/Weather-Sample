package com.francescsoftware.weathersample.ui.feature.search.weather.presenter

import android.os.Parcelable
import com.francescsoftware.weathersample.ui.feature.search.navigation.SelectedCity
import com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel.ForecastDayState
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherScreen(
    val selectedCity: SelectedCity,
) : Screen, Parcelable {

    sealed interface Weather {
        data object Loading : Weather
        data object Error : Weather
        data class Loaded(
            val cityName: String,
            val cityCountryCode: String,
            val current: CurrentWeatherState,
            val forecast: ImmutableList<ForecastDayState>,
        ) : Weather
    }

    data class State(
        val weather: Weather,
        val refreshing: Boolean,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data object RefreshClick : Event
        data object RetryClick : Event
    }
}
