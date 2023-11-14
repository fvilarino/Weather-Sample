package com.francescsoftware.weathersample.ui.feature.search.weather.presenter

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.francescsoftware.weathersample.ui.feature.search.city.model.SelectedCity
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherScreen(
    val selectedCity: SelectedCity,
) : Screen, Parcelable {

    @Immutable
    data class ForecastDayState(
        val header: ForecastHeaderState,
        val forecast: ImmutableList<ForecastHourState>,
    )

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

    @Stable
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
