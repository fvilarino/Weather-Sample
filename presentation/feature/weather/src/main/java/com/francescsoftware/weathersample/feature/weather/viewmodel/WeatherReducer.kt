package com.francescsoftware.weathersample.feature.weather.viewmodel

import com.francescsoftware.weathersample.shared.mvi.Reducer
import javax.inject.Inject

internal class WeatherReducer @Inject constructor() : Reducer<WeatherState, WeatherAction> {
    override fun reduce(
        state: WeatherState,
        action: WeatherAction,
    ): WeatherState = when (action) {
        WeatherAction.Loading -> state.copy(
            loadState = WeatherLoadState.Loading,
        )

        is WeatherAction.CityUpdated -> state.copy(
            cityName = action.cityName,
            cityCountryCode = action.countryCode,
        )

        is WeatherAction.Loaded -> state.copy(
            loadState = WeatherLoadState.Loaded,
            todayState = action.currentWeather,
            forecastItems = action.forecastItems,
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
