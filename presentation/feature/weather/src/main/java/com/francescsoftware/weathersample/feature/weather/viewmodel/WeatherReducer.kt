package com.francescsoftware.weathersample.feature.weather.viewmodel

import com.francescsoftware.weathersample.feature.weather.WeatherAction
import com.francescsoftware.weathersample.feature.weather.WeatherLoadState
import com.francescsoftware.weathersample.feature.weather.WeatherState
import com.francescsoftware.weathersample.shared.mvi.Reducer
import javax.inject.Inject

internal class WeatherReducer @Inject constructor() : Reducer<WeatherState, WeatherAction> {
    override fun reduce(
        state: WeatherState,
        action: WeatherAction,
    ): WeatherState = when (action) {
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
        is WeatherAction.OnOptionSelected -> state.copy(
            option = action.option,
        )
        else -> state
    }
}
