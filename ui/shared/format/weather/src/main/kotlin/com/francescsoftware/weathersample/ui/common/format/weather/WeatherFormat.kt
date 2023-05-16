package com.francescsoftware.weathersample.ui.common.format.weather

import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.ui.shared.format.weather.R
import kotlin.math.roundToInt

/**
 * Formats the [AverageVisibility] for display
 *
 * @param stringLookup the [StringLookup] to access resources
 * @return the formatted average visibility
 */
fun AverageVisibility.format(
    stringLookup: StringLookup,
): String = stringLookup.getString(
    R.string.formatted_visibility,
    kilometers.roundToInt(),
)

/**
 * Formats the [Humidity] for display
 *
 * @param stringLookup the [StringLookup] to access resources
 * @return the formatted humidity
 */
fun Humidity.format(
    stringLookup: StringLookup,
): String = stringLookup.getString(
    R.string.formatted_humidity,
    value,
)

/**
 * Formats the [Pressure] for display
 *
 * @param stringLookup the [StringLookup] to access resources
 * @return the formatted atmospheric pressure
 */
fun Pressure.format(
    stringLookup: StringLookup,
): String = stringLookup.getString(
    R.string.formatted_pressure,
    millibars.roundToInt(),
)

/**
 * Formats the [Speed] for display
 *
 * @param stringLookup the [StringLookup] to access resources
 * @return the formatted speed
 */
fun Speed.format(
    stringLookup: StringLookup,
): String = stringLookup.getString(
    R.string.formatted_wind,
    kph,
)

/**
 * Formats the [Temperature] for display
 *
 * @param stringLookup the [StringLookup] to access resources
 * @return the formatted temperature
 */
fun Temperature.format(
    stringLookup: StringLookup,
): String = stringLookup.getString(
    R.string.formatted_temperature,
    celsius,
)
