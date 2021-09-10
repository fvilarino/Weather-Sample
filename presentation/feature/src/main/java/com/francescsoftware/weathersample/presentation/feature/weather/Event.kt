package com.francescsoftware.weathersample.presentation.feature.weather

import com.francescsoftware.weathersample.presentation.shared.mvi.Event
import com.francescsoftware.weathersample.presentation.shared.mvi.MviIntent
import com.francescsoftware.weathersample.presentation.shared.mvi.ReduceAction
import com.francescsoftware.weathersample.presentation.shared.mvi.State

enum class WeatherLoadState {
    IDLE,
    LOADING,
    LOADED,
    ERROR
}

data class TodayState(
    val loadState: WeatherLoadState,
    val option: WeatherSelectorOptions,
    val cityName: String,
    val cityCountryCode: String,
    val todayState: TodayWeatherCardState,
    val forecastItems: List<ForecastItem>,
    val errorMessage: String,
) : State {

    companion object {
        val initial = TodayState(
            loadState = WeatherLoadState.IDLE,
            option = WeatherSelectorOptions.Today,
            cityName = "",
            cityCountryCode = "",
            todayState = TodayWeatherCardState(),
            forecastItems = emptyList(),
            errorMessage = "",
        )
    }
}

sealed class TodayEvent : Event

sealed class TodayMviIntent : MviIntent {
    object RefreshTodayWeather : TodayMviIntent()
    object Retry : TodayMviIntent()
    data class OnOptionSelected(val option: WeatherSelectorOptions) : TodayMviIntent()
}

sealed class TodayReduceAction : ReduceAction {
    data class CityUpdated(val name: String, val countryCode: String) : TodayReduceAction()
    object Loading : TodayReduceAction()
    data class Loaded(
        val currentWeather: TodayWeatherCardState,
        val forecastItems: List<ForecastItem>
    ) : TodayReduceAction()

    data class TodayLoaded(
        val currentWeather: TodayWeatherCardState,
    ) : TodayReduceAction()

    data class LoadError(val message: String) : TodayReduceAction()

    data class OnOptionSelected(val option: WeatherSelectorOptions) : TodayReduceAction()
}

