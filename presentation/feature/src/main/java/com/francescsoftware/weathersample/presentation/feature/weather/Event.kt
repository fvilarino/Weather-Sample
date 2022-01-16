package com.francescsoftware.weathersample.presentation.feature.weather

import com.francescsoftware.weathersample.mvi.Event
import com.francescsoftware.weathersample.mvi.MviIntent
import com.francescsoftware.weathersample.mvi.ReduceAction
import com.francescsoftware.weathersample.mvi.State

enum class WeatherLoadState {
    IDLE,
    LOADING,
    REFRESHING,
    LOADED,
    ERROR
}

enum class SelectedWeatherScreen {
    Today,
    Forecast,
}

data class TodayState(
    val loadState: WeatherLoadState,
    val option: SelectedWeatherScreen,
    val cityName: String,
    val cityCountryCode: String,
    val todayState: TodayWeatherCardState,
    val forecastItems: List<ForecastItem>,
    val errorMessage: String,
) : State {

    companion object {
        val initial = TodayState(
            loadState = WeatherLoadState.IDLE,
            option = SelectedWeatherScreen.Today,
            cityName = "",
            cityCountryCode = "",
            todayState = TodayWeatherCardState(),
            forecastItems = emptyList(),
            errorMessage = "",
        )
    }
}

sealed interface TodayEvent : Event

sealed interface TodayMviIntent : MviIntent {
    object RefreshTodayWeather : TodayMviIntent
    object Retry : TodayMviIntent
    data class OnOptionSelected(val option: SelectedWeatherScreen) : TodayMviIntent
}

sealed interface TodayReduceAction : ReduceAction {
    data class CityUpdated(val name: String, val countryCode: String) : TodayReduceAction
    object Loading : TodayReduceAction
    object Refreshing : TodayReduceAction
    data class Loaded(
        val currentWeather: TodayWeatherCardState,
        val forecastItems: List<ForecastItem>
    ) : TodayReduceAction

    data class TodayLoaded(
        val currentWeather: TodayWeatherCardState,
    ) : TodayReduceAction

    data class LoadError(val message: String) : TodayReduceAction

    data class OnOptionSelected(val option: SelectedWeatherScreen) : TodayReduceAction
}
