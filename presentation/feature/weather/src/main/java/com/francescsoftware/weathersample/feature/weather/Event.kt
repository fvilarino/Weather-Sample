package com.francescsoftware.weathersample.feature.weather

import com.francescsoftware.weathersample.shared.mvi.Action
import com.francescsoftware.weathersample.shared.mvi.State

internal enum class WeatherLoadState {
    Idle,
    Loading,
    Refreshing,
    Loaded,
    Error,
}

internal enum class SelectedWeatherScreen {
    Today,
    Forecast,
}

internal data class WeatherState(
    val loadState: WeatherLoadState,
    val option: SelectedWeatherScreen,
    val cityName: String,
    val cityCountryCode: String,
    val todayState: TodayWeatherCardState,
    val forecastItems: List<ForecastItem>,
    val errorMessage: String,
) : State {

    companion object {
        val initial = WeatherState(
            loadState = WeatherLoadState.Idle,
            option = SelectedWeatherScreen.Today,
            cityName = "",
            cityCountryCode = "",
            todayState = TodayWeatherCardState(),
            forecastItems = emptyList(),
            errorMessage = "",
        )
    }
}

internal sealed interface WeatherAction : Action {
    data class Load(
        val cityName: String,
        val countryCode: String,
    ) : WeatherAction

    data class RefreshTodayWeather(
        val cityName: String,
        val countryCode: String,
    ) : WeatherAction

    data class CityUpdated(
        val cityName: String,
        val countryCode: String,
    ) : WeatherAction

    data class Loaded(
        val currentWeather: TodayWeatherCardState,
        val forecastItems: List<ForecastItem>,
    ) : WeatherAction

    data class TodayLoaded(
        val currentWeather: TodayWeatherCardState,
    ) : WeatherAction

    data class LoadError(
        val message: String,
    ) : WeatherAction

    data class OnOptionSelected(
        val option: SelectedWeatherScreen,
    ) : WeatherAction

    data class Retry(
        val cityName: String,
        val countryCode: String,
    ) : WeatherAction
}
