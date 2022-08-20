package com.francescsoftware.weathersample.feature.weather

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

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

@Preview(showBackground = true, widthDp = 360)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 360)
@Composable
private fun ForecastHeaderPreview() {
    WeatherSampleTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
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
