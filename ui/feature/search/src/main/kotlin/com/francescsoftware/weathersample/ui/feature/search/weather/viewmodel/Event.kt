package com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.mvi.Action
import com.francescsoftware.weathersample.ui.shared.mvi.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal enum class WeatherLoadState {
    Idle,
    Loading,
    Refreshing,
    Loaded,
    Error,
}

@Immutable
internal data class WeatherState(
    val loadState: WeatherLoadState,
    val cityName: String,
    val cityCountryCode: String,
    val todayState: CurrentWeatherState,
    val forecastItems: ImmutableList<ForecastDayState>,
    @StringRes val errorMessage: Int,
) : State {

    companion object {
        val initial = WeatherState(
            loadState = WeatherLoadState.Idle,
            cityName = "",
            cityCountryCode = "",
            todayState = CurrentWeatherState(),
            forecastItems = persistentListOf(),
            errorMessage = 0,
        )
    }
}

internal sealed interface WeatherAction : Action {
    data object Loading : WeatherAction
    data object Refreshing : WeatherAction

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
        val currentWeather: CurrentWeatherState,
        val forecastItems: ImmutableList<ForecastDayState>,
    ) : WeatherAction

    data class TodayLoaded(
        val currentWeather: CurrentWeatherState,
    ) : WeatherAction

    data class LoadError(
        @StringRes val message: Int,
    ) : WeatherAction

    data class Retry(
        val cityName: String,
        val countryCode: String,
    ) : WeatherAction
}
