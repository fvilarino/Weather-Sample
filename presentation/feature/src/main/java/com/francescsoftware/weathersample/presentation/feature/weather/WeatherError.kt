package com.francescsoftware.weathersample.presentation.feature.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun WeatherError(weatherCallbacks: WeatherCallbacks) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.weather_error_loading),
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(MarginDouble))
            OutlinedButton(onClick = { weatherCallbacks.retry() }) {
                Text(
                    text = stringResource(id = R.string.retry),
                    modifier = Modifier.padding(horizontal = MarginDouble),
                )
            }
            Spacer(modifier = Modifier.height(MarginQuad))
        }
    }
}

@Preview(widthDp = 420)
@Composable
private fun WeatherErrorPreview() {
    WeatherSampleTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            WeatherError(
                weatherCallbacks = object : WeatherCallbacks {
                    override fun onOptionSelect(
                        weatherSelectorOptions: WeatherSelectorOptions
                    ) = Unit

                    override fun refreshTodayWeather() = Unit

                    override fun retry() = Unit
                }
            )
        }
    }
}
