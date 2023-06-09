package com.francescsoftware.weathersample.ui.feature.favorites.viewmodel

import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import com.francescsoftware.weathersample.core.time.api.isToday
import com.francescsoftware.weathersample.core.time.api.isTomorrow
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Current
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastDay
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastEntry
import com.francescsoftware.weathersample.ui.feature.favorites.ui.ForecastDayState
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastDate
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.francescsoftware.weathersample.ui.shared.weathericon.drawableId
import com.francescsoftware.weathersample.ui.shared.weathericon.weatherIconFromCode
import kotlinx.collections.immutable.toImmutableList
import java.util.Date
import java.util.Locale

internal fun Current.toWeatherCardState() =
    CurrentWeatherState(
        temperature = temperature,
        feelsLikeTemperature = feelsLike,
        precipitation = precipitation,
        uvIndex = uvIndex,
        description = description.formatDescription(),
        windSpeed = wind,
        gustSpeed = gust,
        humidity = humidity,
        pressure = pressure,
        visibility = visibility,
        iconId = weatherIconFromCode(iconCode).drawableId,
    )

internal fun String.formatDescription(): String =
    replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }

internal fun Forecast.toForecastItems(
    timeFormatter: TimeFormatter
): List<ForecastDayState> = items.map { forecastDay ->
    ForecastDayState(
        header = forecastDay.toForecastHeaderState(timeFormatter),
        forecast = forecastDay
            .entries.map { entry ->
                entry.toForecastCardState(timeFormatter)
            }
            .toImmutableList()
    )
}

private fun ForecastDay.toForecastHeaderState(
    timeFormatter: TimeFormatter,
): ForecastHeaderState =
    ForecastHeaderState(
        id = "header_${date.time}",
        date = date.toHeaderLabel(timeFormatter),
        sunrise = sunrise,
        sunset = sunset,
    )

private fun Date.toHeaderLabel(
    timeFormatter: TimeFormatter,
): ForecastDate = when {
    isToday -> ForecastDate.Today
    isTomorrow -> ForecastDate.Tomorrow
    else -> ForecastDate.Day(timeFormatter.formatDayWithDayOfWeek(this))
}

private fun ForecastEntry.toForecastCardState(
    timeFormatter: TimeFormatter,
): ForecastHourState =
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
