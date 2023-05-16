package com.francescsoftware.weathersample.ui.feature.weather.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.francescsoftware.weathersample.core.coroutines.CloseableCoroutineScope
import com.francescsoftware.weathersample.ui.shared.mvi.MviViewModel
import com.francescsoftware.weathersample.ui.shared.route.SelectedCity
import com.francescsoftware.weathersample.ui.shared.route.WeatherDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
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

    init {
        handleAction(
            WeatherAction.CityUpdated(
                cityName = selectedCity.name,
                countryCode = selectedCity.countryCode,
            )
        )
        handleAction(
            WeatherAction.Load(
                cityName = selectedCity.name,
                countryCode = selectedCity.countryCode,
            )
        )
    }

    fun refreshTodayWeather() {
        handleAction(
            WeatherAction.RefreshTodayWeather(
                cityName = selectedCity.name,
                countryCode = selectedCity.countryCode,
            )
        )
    }

    fun retry() {
        handleAction(
            WeatherAction.Retry(
                cityName = selectedCity.name,
                countryCode = selectedCity.countryCode,
            )
        )
    }
}
