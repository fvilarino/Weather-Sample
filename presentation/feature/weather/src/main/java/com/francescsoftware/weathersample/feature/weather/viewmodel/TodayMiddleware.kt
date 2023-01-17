package com.francescsoftware.weathersample.feature.weather.viewmodel

import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.shared.mvi.Middleware
import com.francescsoftware.weathersample.type.fold
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class TodayMiddleware @Inject constructor(
    private val getTodayWeatherInteractor: GetTodayWeatherInteractor,
    private val stringLookup: StringLookup,
) : Middleware<WeatherState, WeatherAction>() {

    override fun process(
        state: WeatherState,
        action: WeatherAction,
    ) {
        when (action) {
            is WeatherAction.RefreshTodayWeather -> {
                dispatch(WeatherAction.Loading)
                scope.launch {
                    loadTodayWeather(
                        action.cityName,
                        action.countryCode,
                    )
                }
            }
            else -> {}
        }
    }

    private suspend fun loadTodayWeather(
        name: String,
        countryCode: String,
    ) {
        val location = WeatherLocation.City(
            name = name,
            countryCode = countryCode,
        )
        getTodayWeatherInteractor.execute(location).fold(
            onSuccess = { todayWeather ->
                dispatch(
                    WeatherAction.TodayLoaded(
                        currentWeather = todayWeather.toWeatherCardState(stringLookup),
                    )
                )
            },
            onFailure = {
                dispatch(
                    WeatherAction.LoadError(
                        message = stringLookup.getString(R.string.failed_to_load_weather_data)
                    )
                )
            }
        )
    }
}
