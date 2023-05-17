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
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.francescsoftware.weathersample.ui.shared.format.weather.drawableId
import com.francescsoftware.weathersample.ui.shared.format.weather.format
import com.francescsoftware.weathersample.ui.shared.format.weather.weatherIconFromCode
import com.francescsoftware.weathersample.ui.shared.lookup.api.StringLookup
import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt

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
            val currentAsync = async { getTodayWeatherInteractor(location) }
            val forecastAsync = async { getForecastInteractor(location) }
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
                .toImmutableList()
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
            temperature = temperature.format(stringLookup),
            feelsLikeTemperature = feelsLike.format(stringLookup),
            precipitation = precipitation.millimeters.roundToInt().toString(),
            uvIndex = uvIndex.toString(),
            windSpeed = windSpeed.format(stringLookup),
            humidity = humidity.format(stringLookup),
            visibility = visibility.format(stringLookup),
        )
}
