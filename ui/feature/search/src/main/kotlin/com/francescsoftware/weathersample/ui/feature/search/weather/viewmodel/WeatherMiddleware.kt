package com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel

import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import com.francescsoftware.weathersample.core.time.api.isToday
import com.francescsoftware.weathersample.core.time.api.isTomorrow
import com.francescsoftware.weathersample.core.type.either.valueOrNull
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastDay
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastEntry
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastDate
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
import com.francescsoftware.weathersample.ui.shared.weathericon.drawableId
import com.francescsoftware.weathersample.ui.shared.weathericon.weatherIconFromCode
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject

internal class WeatherMiddleware @Inject constructor(
    private val getTodayWeatherInteractor: GetTodayWeatherInteractor,
    private val getForecastInteractor: GetForecastInteractor,
    private val timeFormatter: TimeFormatter,
) : Middleware<WeatherState, WeatherAction>() {

    override fun process(
        state: WeatherState,
        action: WeatherAction,
    ) {
        when (action) {
            is WeatherAction.Retry -> scope.onLoad(action.cityName, action.countryCode)
            is WeatherAction.Load -> scope.onLoad(action.cityName, action.countryCode)
            else -> {}
        }
    }

    private fun CoroutineScope.onLoad(
        cityName: String,
        countryCode: String,
    ) {
        launch {
            load(
                name = cityName,
                countryCode = countryCode,
            )
        }
        dispatch(WeatherAction.Loading)
    }

    private suspend fun load(
        name: String,
        countryCode: String,
    ) {
        coroutineScope {
            val location = WeatherLocation.City(
                name = name,
                countryCode = countryCode,
            )
            val currentAsync = async { getTodayWeatherInteractor(location) }
            val forecastAsync = async { getForecastInteractor(location) }
            val current = currentAsync.await().valueOrNull()
            val forecast = forecastAsync.await().valueOrNull()
            if (current != null && forecast != null) {
                dispatch(
                    WeatherAction.Loaded(
                        currentWeather = current.toWeatherCardState(),
                        forecastItems = forecast.toForecastItems()
                    )
                )
            } else {
                dispatch(
                    WeatherAction.LoadError(
                        message = R.string.failed_to_load_weather_data,
                    )
                )
            }
        }
    }

    private fun Forecast.toForecastItems(): ImmutableList<ForecastDayState> = items.map { forecastDay ->
        ForecastDayState(
            header = forecastDay.toForecastHeaderState(),
            forecast = forecastDay
                .entries.map { entry ->
                    entry.toForecastCardState()
                }
                .toImmutableList()
        )
    }.toImmutableList()

    private fun ForecastDay.toForecastHeaderState(): ForecastHeaderState =
        ForecastHeaderState(
            id = "header_${date.time}",
            date = date.toHeaderLabel(),
            sunrise = sunrise,
            sunset = sunset,
        )

    private fun Date.toHeaderLabel(): ForecastDate = when {
        isToday -> ForecastDate.Today
        isTomorrow -> ForecastDate.Tomorrow
        else -> ForecastDate.Day(timeFormatter.formatDayWithDayOfWeek(this))
    }

    private fun ForecastEntry.toForecastCardState(): ForecastHourState =
        ForecastHourState(
            id = date.time,
            time = timeFormatter.formatHour(date),
            description = description.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            },
            iconId = weatherIconFromCode(iconCode).drawableId,
            temperature = temperature,
            feelsLikeTemperature = feelsLike,
            precipitation = precipitation,
            uvIndex = uvIndex,
            windSpeed = windSpeed,
            humidity = humidity,
            visibility = visibility,
        )
}
