package com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel

import com.francescsoftware.weathersample.ui.shared.mvi.Reducer
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class WeatherReducer @Inject constructor() : Reducer<WeatherState, WeatherAction> {
    override fun reduce(
        state: WeatherState,
        action: WeatherAction,
    ): WeatherState = when (action) {
        WeatherAction.Loading -> state.copy(
            loadState = WeatherLoadState.Loading,
        )

        WeatherAction.Refreshing -> state.copy(
            loadState = WeatherLoadState.Refreshing,
        )

        is WeatherAction.CityUpdated -> state.copy(
            cityName = action.cityName,
            cityCountryCode = action.countryCode,
        )

        is WeatherAction.Loaded -> state.copy(
            loadState = WeatherLoadState.Loaded,
            todayState = action.currentWeather,
            forecastItems = action.forecastItems.toImmutableList(),
        )

        is WeatherAction.TodayLoaded -> state.copy(
            loadState = WeatherLoadState.Loaded,
            todayState = action.currentWeather,
        )

        is WeatherAction.LoadError -> state.copy(
            loadState = WeatherLoadState.Error,
            errorMessage = action.message,
        )

        else -> state
    }
}
