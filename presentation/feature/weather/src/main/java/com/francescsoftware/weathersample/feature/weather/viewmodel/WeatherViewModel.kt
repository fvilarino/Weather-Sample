package com.francescsoftware.weathersample.feature.weather.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.francescsoftware.weathersample.feature.navigation.api.NavigationDestination
import com.francescsoftware.weathersample.feature.navigation.api.SelectedCity
import com.francescsoftware.weathersample.feature.weather.SelectedWeatherScreen
import com.francescsoftware.weathersample.feature.weather.WeatherAction
import com.francescsoftware.weathersample.feature.weather.WeatherState
import com.francescsoftware.weathersample.shared.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    reducer: WeatherReducer,
    weatherMiddleware: WeatherMiddleware,
    todayMiddleware: TodayMiddleware,
) : MviViewModel<WeatherState, WeatherAction>(
    reducer = reducer,
    middlewares = listOf(weatherMiddleware, todayMiddleware),
    initialState = WeatherState.initial,
), WeatherCallbacks {

    private val selectedCity: SelectedCity = NavigationDestination.Weather.getCity(savedStateHandle)

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

    override fun onOptionSelect(selectedWeatherScreen: SelectedWeatherScreen) {
        handleAction(WeatherAction.OnOptionSelected(selectedWeatherScreen))
    }

    override fun refreshTodayWeather() {
        handleAction(
            WeatherAction.RefreshTodayWeather(
                cityName = selectedCity.name,
                countryCode = selectedCity.countryCode,
            )
        )
    }

    override fun retry() {
        handleAction(
            WeatherAction.Retry(
                cityName = selectedCity.name,
                countryCode = selectedCity.countryCode,
            )
        )
    }
}
