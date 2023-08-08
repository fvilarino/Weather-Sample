package com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel

import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
import javax.inject.Inject

internal class TodayMiddleware @Inject constructor(
    private val getTodayWeatherInteractor: GetTodayWeatherInteractor,
) : Middleware<WeatherState, WeatherAction>() {

    override suspend fun process(
        state: WeatherState,
        action: WeatherAction,
    ) {
        when (action) {
            is WeatherAction.RefreshTodayWeather -> refreshWeather(action)
            else -> {}
        }
    }

    private suspend fun refreshWeather(
        action: WeatherAction.RefreshTodayWeather
    ) {
        dispatch(WeatherAction.Refreshing)
        loadTodayWeather(
            action.cityName,
            action.countryCode,
        )
    }

    private suspend fun loadTodayWeather(
        name: String,
        countryCode: String,
    ) {
        val location = WeatherLocation.City(
            name = name,
            countryCode = countryCode,
        )
        getTodayWeatherInteractor(location).fold(
            onSuccess = { todayWeather ->
                dispatch(
                    WeatherAction.TodayLoaded(
                        currentWeather = todayWeather.toWeatherCardState(),
                    )
                )
            },
            onFailure = {
                dispatch(
                    WeatherAction.LoadError(
                        message = R.string.failed_to_load_weather_data,
                    )
                )
            }
        )
    }
}
