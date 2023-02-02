package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.feature.weather.viewmodel.ForecastHourState
import com.francescsoftware.weathersample.shared.composable.InfoLabels
import com.francescsoftware.weathersample.styles.CardElevation
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

@Composable
internal fun ForecastWeatherCard(
    state: ForecastHourState,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(MarginSingle)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = state.iconId),
                    contentDescription = null,
                    modifier = Modifier.padding(start = MarginDouble).size(64.dp)
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
}

@WidgetPreviews
@Composable
internal fun ForecastWeatherCardPreview(
    @PreviewParameter(ForecastStateProvider::class) forecastState: ForecastHourState,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            ForecastWeatherCard(
                state = forecastState,
                modifier = Modifier.padding(all = MarginSingle),
            )
        }
    }
}
