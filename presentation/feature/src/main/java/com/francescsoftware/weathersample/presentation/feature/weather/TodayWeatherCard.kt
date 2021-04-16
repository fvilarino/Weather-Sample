package com.francescsoftware.weathersample.presentation.feature.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.presentation.shared.R
import com.francescsoftware.weathersample.presentation.shared.widget.InfoLabels
import com.francescsoftware.weathersample.styles.CardElevation
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.presentation.feature.R as featureR

@Composable
fun TodayWeatherCard(
    state: TodayWeatherCardState,
    modifier: Modifier = Modifier,
    elevation: Dp = CardElevation,
) {
    Card(
        modifier = modifier,
        elevation = elevation,
    ) {
        Column(modifier = Modifier.padding(all = MarginSingle)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = state.iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(3f)
                        .height(80.dp),
                )
                Column(
                    modifier = Modifier.weight(4f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = state.temperature, style = MaterialTheme.typography.h4)
                    Text(text = state.description, style = MaterialTheme.typography.body1)
                }
                Column(
                    modifier = Modifier.weight(3f),
                ) {
                    InfoLabels {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            Text(
                                text = stringResource(id = featureR.string.min_temperature_label),
                                style = MaterialTheme.typography.overline,
                            )
                        }
                        Text(
                            text = state.minTemperature,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = MarginSingle),
                        )
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            Text(
                                text = stringResource(id = featureR.string.max_temperature_label),
                                style = MaterialTheme.typography.overline,
                            )
                        }
                        Text(
                            text = state.maxTemperature,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = MarginSingle),
                        )
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            Text(
                                text = stringResource(id = featureR.string.feels_like_label),
                                style = MaterialTheme.typography.overline,
                            )
                        }
                        Text(
                            text = state.feelsLikeTemperature,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = MarginSingle),
                        )
                    }
                }
            }
            Row {
                InfoLabels(modifier = Modifier.weight(1f)) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = stringResource(id = featureR.string.wind_speed_label),
                            style = MaterialTheme.typography.overline,
                        )
                    }
                    Text(
                        text = state.windSpeed,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = stringResource(id = featureR.string.humidity_label),
                            style = MaterialTheme.typography.overline,
                        )
                    }
                    Text(
                        text = state.humidity,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                }
                InfoLabels(modifier = Modifier.weight(1f)) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = stringResource(id = featureR.string.pressure_label),
                            style = MaterialTheme.typography.overline,
                        )
                    }
                    Text(
                        text = state.pressure,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = stringResource(id = featureR.string.visibility_label),
                            style = MaterialTheme.typography.overline,
                        )
                    }
                    Text(
                        text = state.visibility,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodayWeatherCardPreview() {
    WeatherSampleTheme {
        Surface(modifier = Modifier.width(360.dp)) {
            val state = TodayWeatherCardState(
                temperature = "16.4°C",
                minTemperature = "11.3°C",
                maxTemperature = "22.7°C",
                feelsLikeTemperature = "14.3°C",
                description = "Partly cloudy",
                iconId = R.drawable.ic_partly_cloudy,
                windSpeed = "4.3kph",
                humidity = "54%",
                pressure = "1024mb",
                visibility = "10000 m"
            )
            TodayWeatherCard(state = state)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodayWeatherCardPreviewDarkMode() {
    WeatherSampleTheme(darkTheme = true) {
        Surface(modifier = Modifier.width(360.dp)) {
            val state = TodayWeatherCardState(
                temperature = "16.4°C",
                minTemperature = "11.3°C",
                maxTemperature = "22.7°C",
                feelsLikeTemperature = "14.3°C",
                description = "Partly cloudy",
                iconId = R.drawable.ic_partly_cloudy,
                windSpeed = "4.3kph",
                humidity = "54%",
                pressure = "1024mb",
                visibility = "10000 m"
            )
            TodayWeatherCard(
                state = state,
                modifier = Modifier.padding(all = MarginSingle)
            )
        }
    }
}
