package com.francescsoftware.weathersample.feature.weather.viewmodel

import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.interactor.weather.api.TodayMain
import com.francescsoftware.weathersample.lookup.api.StringLookup
import java.util.Locale

internal fun Double.formatTemperature(
    stringLookup: StringLookup,
): String = stringLookup.getString(
    R.string.formatted_temperature,
    this,
)

internal fun TodayMain.formatDescription(): String =
    description.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }

internal fun Double.formatWind(
    stringLookup: StringLookup,
): String = stringLookup.getString(
    R.string.formatted_wind,
    this,
)

internal fun Int.formatHumidity(
    stringLookup: StringLookup,
): String = stringLookup.getString(
    R.string.formatted_humidity,
    this,
)

internal fun Int.formatPressure(
    stringLookup: StringLookup,
): String = stringLookup.getString(
    R.string.formatted_pressure,
    this,
)

internal fun Int.formatVisibility(
    stringLookup: StringLookup,
): String = stringLookup.getString(
    R.string.formatted_visibility,
    this,
)
