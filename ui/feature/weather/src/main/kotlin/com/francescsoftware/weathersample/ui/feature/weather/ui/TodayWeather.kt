package com.francescsoftware.weathersample.ui.feature.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.francescsoftware.weathersample.ui.feature.weather.R
import com.francescsoftware.weathersample.ui.feature.weather.viewmodel.WeatherLoadState
import com.francescsoftware.weathersample.ui.feature.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.ui.shared.composable.common.LoadingButton
import com.francescsoftware.weathersample.ui.shared.composable.weather.TodayWeatherCard
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginQuad
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme

@Composable
internal fun TodayWeather(
    state: WeatherState,
    todayRefreshCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isLoaded = state.loadState == WeatherLoadState.Loaded
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TodayWeatherCard(
            state = state.todayState,
            modifier = Modifier.width(WeatherCardWidth),
        )
        LoadingButton(
            onClick = todayRefreshCallback,
            enabled = isLoaded,
            modifier = Modifier.padding(top = MarginQuad),
            loading = state.loadState == WeatherLoadState.Refreshing,
        ) {
            Text(text = stringResource(id = R.string.refresh))
        }
    }
}

@PhonePreviews
@Composable
private fun PreviewTodayWeather(
    @PreviewParameter(
        provider = WeatherStateWrapperProvider::class,
        limit = 1,
    ) stateWrapper: WeatherStateWrapper,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            TodayWeather(
                state = stateWrapper.state,
                todayRefreshCallback = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
            )
        }
    }
}
