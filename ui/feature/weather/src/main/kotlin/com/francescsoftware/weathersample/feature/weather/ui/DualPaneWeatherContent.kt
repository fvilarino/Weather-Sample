package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.TabletPreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun DualPaneWeatherContent(
    state: WeatherState,
    todayRefreshCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            CityLabel(
                cityName = state.cityName,
                countryCode = state.cityCountryCode,
                modifier = Modifier
                    .padding(top = MarginDouble)
                    .align(Alignment.TopCenter),
            )
            TodayWeather(
                state = state,
                todayRefreshCallback = todayRefreshCallback,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
            )
        }
        WeatherForecast(
            state = state,
            modifier = Modifier.weight(2f),
        )
    }
}

@TabletPreviews
@Composable
private fun PreviewDualPaneWeatherContent(
    @PreviewParameter(
        provider = WeatherStateWrapperProvider::class,
        limit = 1,
    ) stateWrapper: WeatherStateWrapper,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            DualPaneWeatherContent(
                state = stateWrapper.state,
                todayRefreshCallback = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
            )
        }
    }
}
