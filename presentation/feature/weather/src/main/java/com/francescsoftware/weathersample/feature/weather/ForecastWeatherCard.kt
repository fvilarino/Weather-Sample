package com.francescsoftware.weathersample.feature.weather

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.shared.composable.InfoLabels
import com.francescsoftware.weathersample.styles.CardElevation
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.MarginTreble
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun ForecastWeatherCard(
    state: ForecastItem.ForecastCard,
    modifier: Modifier = Modifier,
    elevation: Dp = CardElevation,
) {
    Card(modifier = modifier, elevation = elevation) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MarginSingle)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = state.iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp),
                )
                Text(
                    text = state.header,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MarginTreble)
            ) {
                InfoLabels(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = MarginDouble)
                ) {
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
                InfoLabels(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = MarginDouble)
                ) {
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

@Preview(widthDp = 360)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 360)
@Composable
private fun ForecastWeatherCardPreview(
    @PreviewParameter(ForecastStateProvider::class) forecastState: ForecastItem.ForecastCard,
) {
    WeatherSampleTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            ForecastWeatherCard(
                state = forecastState,
                modifier = Modifier.padding(all = MarginSingle),
            )
        }
    }
}
