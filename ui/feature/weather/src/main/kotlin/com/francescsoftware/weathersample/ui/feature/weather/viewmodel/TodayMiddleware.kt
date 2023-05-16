package com.francescsoftware.weathersample.ui.feature.weather.viewmodel

import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.ui.feature.weather.R
import com.francescsoftware.weathersample.ui.shared.lookup.api.StringLookup
import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
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
                dispatch(WeatherAction.Refreshing)
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
        getTodayWeatherInteractor(location).fold(
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
