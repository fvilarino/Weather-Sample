package com.francescsoftware.weathersample.ui.shared.composable.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

/**
 * A weather forecast header
 *
 * @param state the state for the header
 * @param modifier the [Modifier] to apply to this header
 */
@Composable
fun ForecastHeader(
    state: ForecastHeaderState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = state.date,
            style = MaterialTheme.typography.titleLarge,
        )
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = stringResource(id = R.string.sunrise_label),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.alignByBaseline(),
            )
            Spacer(modifier = Modifier.width(MarginSingle))
            Text(
                text = state.sunrise,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.alignByBaseline(),
            )
            Spacer(modifier = Modifier.width(MarginDouble))
            Text(
                text = stringResource(id = R.string.sunset_label),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.alignByBaseline(),
            )
            Spacer(modifier = Modifier.width(MarginSingle))
            Text(
                text = state.sunset,
                style = MaterialTheme.typography.bodySmall,
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
            color = MaterialTheme.colorScheme.background,
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
