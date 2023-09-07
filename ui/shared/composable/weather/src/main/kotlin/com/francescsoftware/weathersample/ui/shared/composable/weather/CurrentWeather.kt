package com.francescsoftware.weathersample.ui.shared.composable.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.InfoLabels
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.MarginTreble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

private val WeatherIconHeight = 80.dp
private val InfoIconSize = 20.dp
private const val SideColumnWeight = 1f
private const val CentralColumnWeight = 1.7f * SideColumnWeight

/**
 * Displays the current weather
 *
 * @param state the current weather state
 * @param modifier the [Modifier] to be applied to the current weather
 */
@Composable
fun CurrentWeather(
    state: CurrentWeatherState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = state.iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(SideColumnWeight)
                        .height(WeatherIconHeight)
                        .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true),
                )
                Column(
                    modifier = Modifier.weight(CentralColumnWeight),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.formatted_temperature_celsius,
                            state.temperature.celsius,
                        ),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = state.description,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Column(
                    modifier = Modifier.weight(SideColumnWeight),
                ) {
                    InfoLabels {
                        Icon(
                            imageVector = Icons.Default.Thermostat,
                            modifier = Modifier.size(InfoIconSize),
                            contentDescription = null,
                        )
                        WeatherItemContent(
                            label = state.feelsLikeTemperature.format(),
                            modifier = Modifier.padding(start = MarginSingle),
                        )
                        Icon(
                            imageVector = Icons.Default.WaterDrop,
                            modifier = Modifier.size(InfoIconSize),
                            contentDescription = null,
                        )
                        WeatherItemContent(
                            label = state.precipitation.format(),
                            modifier = Modifier.padding(start = MarginSingle),
                        )
                        Icon(
                            imageVector = Icons.Default.WindPower,
                            modifier = Modifier.size(InfoIconSize),
                            contentDescription = null,
                        )
                        WeatherItemContent(
                            label = state.windSpeed.format(),
                            modifier = Modifier.padding(start = MarginSingle),
                        )
                    }
                }
            }
            Row {
                InfoLabels(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = MarginTreble),
                ) {
                    WeatherItemLabel(
                        label = stringResource(id = R.string.gust_speed_label),
                    )
                    WeatherItemContent(
                        label = state.gustSpeed.format(),
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                    WeatherItemLabel(
                        label = stringResource(id = R.string.humidity_label),
                    )
                    WeatherItemContent(
                        label = state.humidity.format(),
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                }
                InfoLabels(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = MarginTreble),
                ) {
                    WeatherItemLabel(
                        label = stringResource(id = R.string.pressure_label),
                    )
                    WeatherItemContent(
                        label = state.pressure.format(),
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                    WeatherItemLabel(
                        label = stringResource(id = R.string.visibility_label),
                    )
                    WeatherItemContent(
                        label = state.visibility.format(),
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                }
            }
        }
    }
}

@WidgetPreviews
@Composable
private fun PreviewTodayWeatherCard() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val state = CurrentWeatherState(
                temperature = Temperature.fromCelsius(16.4),
                feelsLikeTemperature = Temperature.fromCelsius(14.3),
                precipitation = Precipitation.fromMillimeters(10.0),
                uvIndex = UvIndex(3),
                description = "Partly cloudy",
                iconId = com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_partly_cloudy,
                windSpeed = Speed.fromKph(4.3),
                gustSpeed = Speed.fromKph(7.5),
                humidity = Humidity(54),
                pressure = Pressure.fromMillibars(1024.0),
                visibility = AverageVisibility.fromKm(10.0),
            )
            CurrentWeather(state = state)
        }
    }
}
