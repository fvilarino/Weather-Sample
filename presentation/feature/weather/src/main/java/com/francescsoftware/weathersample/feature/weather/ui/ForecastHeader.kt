package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.feature.weather.viewmodel.ForecastHeaderState
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

@Composable
internal fun ForecastHeader(
    state: ForecastHeaderState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = state.date,
            style = MaterialTheme.typography.h6,
        )
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = stringResource(id = R.string.sunrise_label),
                style = MaterialTheme.typography.overline,
                modifier = Modifier.alignByBaseline(),
            )
            Spacer(modifier = Modifier.width(MarginSingle))
            Text(
                text = state.sunrise,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.alignByBaseline(),
            )
            Spacer(modifier = Modifier.width(MarginDouble))
            Text(
                text = stringResource(id = R.string.sunset_label),
                style = MaterialTheme.typography.overline,
                modifier = Modifier.alignByBaseline(),
            )
            Spacer(modifier = Modifier.width(MarginSingle))
            Text(
                text = state.sunset,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.alignByBaseline(),
            )
        }
    }
}

@WidgetPreviews
@Composable
private fun ForecastHeaderPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            ForecastHeader(
                state = ForecastHeaderState(
                    id = "header",
                    date = "Sun April 18",
                    sunrise = "06:23",
                    sunset = "20:01",
                ),
                modifier = Modifier.padding(vertical = MarginSingle),
            )
        }
    }
}
