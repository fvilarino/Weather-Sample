package com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.francescsoftware.weathersample.core.coroutines.CloseableCoroutineScope
import com.francescsoftware.weathersample.ui.feature.search.navigation.SelectedCity
import com.francescsoftware.weathersample.ui.feature.search.navigation.WeatherDestination
import com.francescsoftware.weathersample.ui.shared.mvi.MviViewModel
import javax.inject.Inject

internal class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    closeableScope: CloseableCoroutineScope,
    reducer: WeatherReducer,
    weatherMiddleware: WeatherMiddleware,
    todayMiddleware: TodayMiddleware,
) : MviViewModel<WeatherState, WeatherAction>(
    closeableScope = closeableScope,
    reducer = reducer,
    middlewares = listOf(weatherMiddleware, todayMiddleware),
    initialState = WeatherState.initial,
) {

    private val selectedCity: SelectedCity = WeatherDestination.getCity(savedStateHandle)

    fun onStart() {
        dispatch(
            WeatherAction.CityUpdated(
                cityName = selectedCity.name,
                countryCode = selectedCity.countryCode,
            ),
        )
        dispatch(
            WeatherAction.Load(
                cityName = selectedCity.name,
                countryCode = selectedCity.countryCode,
            ),
        )
    }

    fun refreshTodayWeather() {
        dispatch(
            WeatherAction.RefreshTodayWeather(
                cityName = selectedCity.name,
                countryCode = selectedCity.countryCode,
            ),
        )
    }

    fun retry() {
        dispatch(
            WeatherAction.Retry(
                cityName = selectedCity.name,
                countryCode = selectedCity.countryCode,
            ),
        )
    }
}
