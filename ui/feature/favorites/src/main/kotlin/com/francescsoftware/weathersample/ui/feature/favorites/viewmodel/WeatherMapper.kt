package com.francescsoftware.weathersample.ui.feature.favorites.viewmodel

import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import com.francescsoftware.weathersample.core.time.api.isToday
import com.francescsoftware.weathersample.core.time.api.isTomorrow
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Current
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastDay
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastEntry
import com.francescsoftware.weathersample.ui.feature.favorites.R
import com.francescsoftware.weathersample.ui.feature.favorites.ui.ForecastDayState
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.francescsoftware.weathersample.ui.shared.format.weather.drawableId
import com.francescsoftware.weathersample.ui.shared.format.weather.format
import com.francescsoftware.weathersample.ui.shared.format.weather.weatherIconFromCode
import com.francescsoftware.weathersample.ui.shared.lookup.api.StringLookup
import kotlinx.collections.immutable.toImmutableList
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

internal fun Current.toWeatherCardState(stringLookup: StringLookup) =
    CurrentWeatherState(
        temperature = temperature.format(stringLookup),
        feelsLikeTemperature = feelsLike.format(stringLookup),
        precipitation = precipitation.millimeters.roundToInt().toString(),
        uvIndex = uvIndex.toString(),
        description = description.formatDescription(),
        windSpeed = wind.format(stringLookup),
        humidity = humidity.format(stringLookup),
        pressure = pressure.format(stringLookup),
        visibility = visibility.format(stringLookup),
        iconId = weatherIconFromCode(iconCode).drawableId,
    )

internal fun String.formatDescription(): String =
    replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }

internal fun Forecast.toForecastItems(
    stringLookup: StringLookup,
    timeFormatter: TimeFormatter,
): List<ForecastDayState> = items.map { forecastDay ->
    ForecastDayState(
        header = forecastDay.toForecastHeaderState(stringLookup, timeFormatter),
        forecast = forecastDay
            .entries.map { entry ->
                entry.toForecastCardState(stringLookup, timeFormatter)
            }
            .toImmutableList()
    )
}

private fun ForecastDay.toForecastHeaderState(
    stringLookup: StringLookup,
    timeFormatter: TimeFormatter,
): ForecastHeaderState =
    ForecastHeaderState(
        id = "header_${date.time}",
        date = date.toHeaderLabel(stringLookup, timeFormatter),
        sunrise = sunrise,
        sunset = sunset,
    )

private fun Date.toHeaderLabel(
    stringLookup: StringLookup,
    timeFormatter: TimeFormatter,
): String = when {
    isToday -> stringLookup.getString(R.string.today)
    isTomorrow -> stringLookup.getString(R.string.tomorrow)
    else -> timeFormatter.formatDayWithDayOfWeek(this)
}

private fun ForecastEntry.toForecastCardState(
    stringLookup: StringLookup,
    timeFormatter: TimeFormatter,
): ForecastHourState =
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
        precipitation = precipitation.millimeters.toString(),
        uvIndex = uvIndex.toString(),
        windSpeed = windSpeed.format(stringLookup),
        humidity = humidity.format(stringLookup),
        visibility = visibility.format(stringLookup),
    )
