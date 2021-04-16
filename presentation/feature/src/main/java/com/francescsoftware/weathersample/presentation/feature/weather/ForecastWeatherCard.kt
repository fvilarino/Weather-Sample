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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.shared.widget.InfoLabels
import com.francescsoftware.weathersample.styles.CardElevation
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
fun ForecastWeatherCard(
    state: ForecastItem.ForecastCard,
    modifier: Modifier = Modifier,
    elevation: Dp = CardElevation,
) {
    Card(modifier = modifier, elevation = elevation) {
        Column(modifier = Modifier.fillMaxWidth().padding(MarginSingle)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = state.iconId),
                    contentDescription = null,
                    modifier = Modifier.width(64.dp).height(64.dp),
                )
                Text(
                    text = state.header,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                InfoLabels(modifier = Modifier.weight(1f)) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = stringResource(id = R.string.min_temperature_label),
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
                            text = stringResource(id = R.string.max_temperature_label),
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
                            text = stringResource(id = R.string.feels_like_label),
                            style = MaterialTheme.typography.overline,
                        )
                    }
                    Text(
                        text = state.feelsLikeTemperature,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                }
                InfoLabels(modifier = Modifier.weight(1f)) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = stringResource(id = R.string.wind_speed_label),
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
                            text = stringResource(id = R.string.humidity_label),
                            style = MaterialTheme.typography.overline,
                        )
                    }
                    Text(
                        text = state.humidity,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = MarginSingle),
                    )
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = stringResource(id = R.string.visibility_label),
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
private fun ForecastWeatherCardPreview() {
    WeatherSampleTheme {
        Surface(modifier = Modifier.width(360.dp)) {
            ForecastWeatherCard(
                state = ForecastItem.ForecastCard(
                    id = 1L,
                    header = "02:00 - Scattered Clouds",
                    iconId = R.drawable.ic_partly_cloudy,
                    minTemperature = "16.4°C",
                    maxTemperature = "23.7°C",
                    feelsLikeTemperature = "15.5°C",
                    windSpeed = "5.4 kph",
                    humidity = "48 %",
                    visibility = "10000 m",
                ),
                modifier = Modifier.padding(vertical = MarginSingle),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForecastWeatherCardPreviewDarkMode() {
    WeatherSampleTheme(darkTheme = true) {
        Surface(modifier = Modifier.width(360.dp)) {
            ForecastWeatherCard(
                state = ForecastItem.ForecastCard(
                    id = 1L,
                    header = "02:00 - Scattered Clouds",
                    iconId = R.drawable.ic_partly_cloudy,
                    minTemperature = "16.4°C",
                    maxTemperature = "23.7°C",
                    feelsLikeTemperature = "15.5°C",
                    windSpeed = "5.4 kph",
                    humidity = "48 %",
                    visibility = "10000 m",
                ),
                modifier = Modifier.padding(vertical = MarginSingle),
            )
        }
    }
}
