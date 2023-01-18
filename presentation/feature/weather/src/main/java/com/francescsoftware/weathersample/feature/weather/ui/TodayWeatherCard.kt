package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.feature.weather.viewmodel.TodayWeatherCardState
import com.francescsoftware.weathersample.shared.composable.InfoLabels
import com.francescsoftware.weathersample.styles.CardElevation
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.MarginTreble
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

@Composable
internal fun TodayWeatherCard(
    state: TodayWeatherCardState,
    modifier: Modifier = Modifier,
    elevation: Dp = CardElevation,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
        ),
    ) {
        Column(
            modifier = Modifier.padding(all = MarginSingle),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = state.iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(2.5f)
                        .height(80.dp)
                        .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true),
                )
                Column(
                    modifier = Modifier.weight(5f),
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
                    modifier = Modifier.weight(2.5f),
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
            val state = TodayWeatherCardState(
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
            TodayWeatherCard(state = state)
        }
    }
}
