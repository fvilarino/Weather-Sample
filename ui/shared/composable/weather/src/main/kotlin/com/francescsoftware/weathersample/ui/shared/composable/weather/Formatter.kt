package com.francescsoftware.weathersample.ui.shared.composable.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature

private val UnitsFontSize = 8.sp

/**
 * Formats the [AverageVisibility] for display
 *
 * @return the formatted average visibility
 */
@Composable
internal fun AverageVisibility.format(): AnnotatedString = format(
    label = kilometers.toString(),
    units = stringResource(id = R.string.visibility_km),
)

/**
 * Formats the [Humidity] for display
 *
 * @return the formatted humidity
 */
@Composable
internal fun Humidity.format() = stringResource(
    id = R.string.formatted_humidity,
    value,
)

@Composable
internal fun Precipitation.format() = format(
    label = millimeters.toString(),
    units = stringResource(R.string.precipitation_millimeters),
)

/**
 * Formats the [Pressure] for display
 *
 * @return the formatted atmospheric pressure
 */
@Composable
internal fun Pressure.format(): AnnotatedString = format(
    label = millibars.toString(),
    units = stringResource(R.string.pressure_millibars),
)

/**
 * Formats the [Speed] for display
 *
 * @return the formatted speed
 */
@Suppress("MagicNumber")
@Composable
internal fun Speed.format(): AnnotatedString = format(
    label = ((kph * 10.0).toInt() / 10f).toString(),
    units = stringResource(id = R.string.wind_speed)
)

/**
 * Formats the [Temperature] for display
 *
 * @return the formatted temperature
 */
@Composable
internal fun Temperature.format(): AnnotatedString = format(
    label = stringResource(id = R.string.formatted_temperature, celsius),
    units = stringResource(id = R.string.temperature_celsius),
)

@Composable
private fun format(
    label: String,
    units: String,
): AnnotatedString = buildAnnotatedString {
    append(label)
    withStyle(style = SpanStyle(fontSize = UnitsFontSize)) {
        append(units)
    }
}
