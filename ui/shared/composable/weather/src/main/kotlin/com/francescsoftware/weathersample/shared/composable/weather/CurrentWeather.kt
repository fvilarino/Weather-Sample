package com.francescsoftware.weathersample.shared.composable.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.francescsoftware.weathersample.shared.composable.common.InfoLabels
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.MarginTreble
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

private val WeatherIconHeight = 80.dp
private const val SideColumnWeight = 1f
private const val CentralColumnWeight = 2f * SideColumnWeight

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
                        text = state.temperature,
                        style = MaterialTheme.typography.headlineSmall,
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
                        WeatherItemLabel(
                            label = stringResource(id = R.string.temperature_label),
                        )
                        WeatherItemContent(
                            label = state.temperature,
                            modifier = Modifier.padding(start = MarginSingle),
                        )
                        WeatherItemLabel(
                            label = stringResource(id = R.string.feels_like_label),
                        )
                        WeatherItemContent(
                            label = state.feelsLikeTemperature,
                            modifier = Modifier.padding(start = MarginSingle),
                        )
                        WeatherItemLabel(
                            label = stringResource(id = R.string.precipitation_label),
                        )
                        WeatherItemContent(
                            label = buildAnnotatedString {
                                append(state.precipitation)
                                withStyle(style = SpanStyle(fontSize = 8.sp)) {
                                    append(stringResource(id = R.string.precipitation_mm))
                                }
                            },
                            modifier = Modifier.padding(start = MarginSingle),
                        )
                    }
                }
            }
            Row {
                InfoLabels(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = MarginTreble)
                ) {
                    WeatherItemLabel(
                        label = stringResource(id = R.string.wind_speed_label),
                    )
                    WeatherItemContent(
                        label = state.windSpeed,
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                    WeatherItemLabel(
                        label = stringResource(id = R.string.humidity_label),
                    )
                    WeatherItemContent(
                        label = state.humidity,
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                }
                InfoLabels(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = MarginTreble)
                ) {
                    WeatherItemLabel(
                        label = stringResource(id = R.string.pressure_label),
                    )
                    WeatherItemContent(
                        label = state.pressure,
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                    WeatherItemLabel(
                        label = stringResource(id = R.string.visibility_label),
                    )
                    WeatherItemContent(
                        label = state.visibility,
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                }
            }
        }
    }
}

@WidgetPreviews
@Composable
private fun TodayWeatherCardPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val state = CurrentWeatherState(
                temperature = "16.4°C",
                feelsLikeTemperature = "14.3°C",
                precipitation = "10",
                uvIndex = "3",
                description = "Partly cloudy",
                iconId = com.francescsoftware.weathersample.shared.assets.R.drawable.ic_partly_cloudy,
                windSpeed = "4.3kph",
                humidity = "54%",
                pressure = "1024mb",
                visibility = "10000 m",
            )
            CurrentWeather(state = state)
        }
    }
}
