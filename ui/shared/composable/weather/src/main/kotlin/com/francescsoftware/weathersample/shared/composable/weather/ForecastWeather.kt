package com.francescsoftware.weathersample.shared.composable.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.francescsoftware.weathersample.shared.composable.common.InfoLabels
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

/**
 * Displays a weather forecast
 *
 * @param state the state representing this forecast
 * @param modifier the [Modifier] to be applied to the weather forecast
 */
@Composable
fun ForecastWeather(
    state: ForecastHourState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = state.iconId),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = MarginDouble)
                    .size(64.dp)
            )
            Text(
                text = state.header,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MarginDouble)
        ) {
            InfoLabels(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = MarginSingle)
            ) {
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
            InfoLabels(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = MarginSingle)
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

private val SunnyHourForecast = ForecastHourState(
    id = 2L,
    header = "14:00 - Sunny",
    iconId = com.francescsoftware.weathersample.shared.assets.R.drawable.ic_sunny,
    temperature = "18.7°C",
    feelsLikeTemperature = "21.5°C",
    precipitation = "10",
    uvIndex = "8",
    windSpeed = "14.1 kph",
    humidity = "75 %",
    visibility = "10 km",
)

@WidgetPreviews
@Composable
private fun ForecastWeatherCardPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            ForecastWeather(
                state = SunnyHourForecast,
                modifier = Modifier.padding(all = MarginSingle),
            )
        }
    }
}
