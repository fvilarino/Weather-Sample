package com.francescsoftware.weathersample.feature.weather.viewmodel

import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.interactor.weather.api.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.shared.mvi.Middleware
import com.francescsoftware.weathersample.time.api.TimeFormatter
import com.francescsoftware.weathersample.type.valueOrNull
import com.francescsoftware.weathersample.utils.time.isToday
import com.francescsoftware.weathersample.utils.time.isTomorrow
import kotlinx.collections.immutable.toPersistentList
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
    private val stringLookup: StringLookup,
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
            val currentAsync = async { getTodayWeatherInteractor.execute(location) }
            val forecastAsync = async { getForecastInteractor.execute(location) }
            val current = currentAsync.await().valueOrNull()
            val forecast = forecastAsync.await().valueOrNull()
            if (current != null && forecast != null) {
                dispatch(
                    WeatherAction.Loaded(
                        currentWeather = current.toWeatherCardState(stringLookup),
                        forecastItems = forecast.toForecastItems()
                    )
                )
            } else {
                dispatch(
                    WeatherAction.LoadError(
                        message = stringLookup.getString(R.string.failed_to_load_weather_data)
                    )
                )
            }
        }
    }

    private fun Forecast.toForecastItems(): List<ForecastDayState> = items.map { forecastDay ->
        ForecastDayState(
            header = forecastDay.toForecastHeaderState(),
            forecast = forecastDay
                .entries.map { entry ->
                    entry.toForecastCardState()
                }
                .toPersistentList()
        )
    }

    private fun ForecastDay.toForecastHeaderState(): ForecastHeaderState =
        ForecastHeaderState(
            id = "header_${date.time}",
            date = date.toHeaderLabel(),
            sunrise = sunrise,
            sunset = sunset,
        )

    private fun Date.toHeaderLabel(): String = when {
        isToday -> stringLookup.getString(R.string.today)
        isTomorrow -> stringLookup.getString(R.string.tomorrow)
        else -> timeFormatter.formatDayWithDayOfWeek(this)
    }

    private fun ForecastEntry.toForecastCardState(): ForecastHourState =
        ForecastHourState(
            id = date.time,
            header = stringLookup.getString(
                R.string.forecast_card_header,
                timeFormatter.formatHour(date),
                description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            ),
            iconId = weatherIconFromCode(iconCode).drawableId,
            temperature = temperature.formatTemperature(stringLookup),
            feelsLikeTemperature = feelsLikeTemperature.formatTemperature(stringLookup),
            precipitation = precipitation.toString(),
            uvIndex = uvIndex.toString(),
            windSpeed = windSpeed.formatWind(stringLookup),
            humidity = humidityPercent.formatHumidity(stringLookup),
            visibility = visibility.formatVisibility(stringLookup),
        )
}
