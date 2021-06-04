package com.francescsoftware.weathersample.presentation.feature.weather.tabs

import com.francescsoftware.weathersample.interactor.weather.TodayWeather
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.shared.lookup.StringLookup
import java.util.*

internal fun Double.formatTemperature(
    stringLookup: StringLookup,
): CharSequence = stringLookup.getString(
    R.string.formatted_temperature,
    this,
)

internal fun TodayWeather.formatDescription(): CharSequence =
    weather.description.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }

internal fun Double.formatWind(
    stringLookup: StringLookup,
): CharSequence = stringLookup.getString(
    R.string.formatted_wind,
    this,
)

internal fun Int.formatHumidity(
    stringLookup: StringLookup,
): CharSequence = stringLookup.getString(
    R.string.formatted_humidity,
    this,
)

internal fun Int.formatPressure(
    stringLookup: StringLookup,
): CharSequence = stringLookup.getString(
    R.string.formatted_pressure,
    this,
)

internal fun Int.formatVisibility(
    stringLookup: StringLookup,
): CharSequence = stringLookup.getString(
    R.string.formatted_visibility,
    this,
)
