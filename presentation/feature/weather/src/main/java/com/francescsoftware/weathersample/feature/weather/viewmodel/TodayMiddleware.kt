package com.francescsoftware.weathersample.feature.weather.viewmodel

import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.feature.weather.WeatherAction
import com.francescsoftware.weathersample.feature.weather.WeatherLoadState
import com.francescsoftware.weathersample.feature.weather.WeatherState
import com.francescsoftware.weathersample.feature.weather.toWeatherCardState
import com.francescsoftware.weathersample.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.shared.mvi.ActionHandler
import com.francescsoftware.weathersample.shared.mvi.Middleware
import com.francescsoftware.weathersample.type.fold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class TodayMiddleware @Inject constructor(
    private val getTodayWeatherInteractor: GetTodayWeatherInteractor,
    private val stringLookup: StringLookup,
) : Middleware<WeatherState, WeatherAction> {

    private lateinit var actionHandler: ActionHandler<WeatherAction>
    private lateinit var scope: CoroutineScope

    override fun setup(
        scope: CoroutineScope,
        actionHandler: ActionHandler<WeatherAction>,
    ) {
        this.actionHandler = actionHandler
        this.scope = scope
    }

    override fun reduce(
        state: WeatherState,
        action: WeatherAction,
    ): WeatherState = when (action) {
        is WeatherAction.RefreshTodayWeather -> {
            scope.launch {
                loadTodayWeather(
                    action.cityName,
                    action.countryCode,
                )
            }
            state.copy(
                loadState = WeatherLoadState.Loading,
            )
        }
        else -> state
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
                actionHandler.handleAction(
                    WeatherAction.TodayLoaded(
                        currentWeather = todayWeather.toWeatherCardState(stringLookup),
                    )
                )
            },
            onFailure = {
                actionHandler.handleAction(
                    WeatherAction.LoadError(
                        message = stringLookup.getString(R.string.failed_to_load_weather_data)
                    )
                )
            }
        )
    }
}
