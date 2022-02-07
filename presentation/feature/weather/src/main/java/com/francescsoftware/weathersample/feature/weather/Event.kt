package com.francescsoftware.weathersample.feature.weather

import com.francescsoftware.weathersample.mvi.Event
import com.francescsoftware.weathersample.mvi.MviIntent
import com.francescsoftware.weathersample.mvi.ReduceAction
import com.francescsoftware.weathersample.mvi.State

internal enum class WeatherLoadState {
    IDLE,
    LOADING,
    REFRESHING,
    LOADED,
    ERROR
}

internal enum class SelectedWeatherScreen {
    Today,
    Forecast,
}

internal data class TodayState(
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

internal sealed interface TodayEvent : Event

internal sealed interface TodayMviIntent : MviIntent {
    object RefreshTodayWeather : TodayMviIntent
    object Retry : TodayMviIntent
    data class OnOptionSelected(val option: SelectedWeatherScreen) : TodayMviIntent
}

internal sealed interface TodayReduceAction : ReduceAction {
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
