package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.PhonePreviews
import com.francescsoftware.weathersample.styles.TabletPreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun WeatherError(
    modifier: Modifier = Modifier,
    retry: () -> Unit = {},
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.weather_error_loading),
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(MarginDouble))
            OutlinedButton(onClick = retry) {
                Text(
                    text = stringResource(id = R.string.retry),
                    modifier = Modifier.padding(horizontal = MarginDouble),
                )
            }
            Spacer(modifier = Modifier.height(MarginQuad))
        }
    }
}

@PhonePreviews
@TabletPreviews
@Composable
internal fun WeatherErrorPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            WeatherError(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble)
            )
        }
    }
}
