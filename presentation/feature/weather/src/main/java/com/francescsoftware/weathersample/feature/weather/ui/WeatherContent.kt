package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.feature.weather.viewmodel.SelectedWeatherScreen
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherLoadState
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.shared.composable.LoadingButton
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.PhonePreviews
import com.francescsoftware.weathersample.styles.TabletPreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

private val WeatherCardWidth = 360.dp

@Composable
internal fun WeatherContent(
    state: WeatherState,
    todayRefreshCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state.option) {
        SelectedWeatherScreen.Today -> TodayWeather(
            state = state,
            todayRefreshCallback = todayRefreshCallback,
            modifier = modifier,
        )

        SelectedWeatherScreen.Forecast -> WeatherForecast(
            state = state,
            modifier = modifier,
        )
    }
}

@Composable
private fun TodayWeather(
    state: WeatherState,
    todayRefreshCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            modifier = Modifier.padding(top = MarginQuad),
            loading = state.loadState == WeatherLoadState.Refreshing,
        ) {
            Text(text = stringResource(id = R.string.refresh))
        }
    }
}

@Composable
private fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(
            minSize = WeatherCardWidth,
        ),
        modifier = modifier,
        contentPadding = PaddingValues(all = MarginDouble),
        horizontalArrangement = Arrangement.spacedBy(MarginDouble),
        verticalArrangement = Arrangement.spacedBy(MarginDouble),
    ) {
        state.forecastItems.forEach { dayForecast ->
            item(
                key = dayForecast.header.id,
                span = { GridItemSpan(maxLineSpan) }
            ) {
                ForecastHeader(
                    state = dayForecast.header,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MarginDouble),
                )
            }
            items(
                items = dayForecast.forecast,
                key = { hourForecast -> hourForecast.id }
            ) { hourForecast ->
                ForecastWeatherCard(
                    state = hourForecast,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@PhonePreviews
@TabletPreviews
@Composable
private fun PreviewWeatherContent(
    @PreviewParameter(WeatherStateProvider::class, limit = 2) state: WeatherState,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            WeatherContent(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
                todayRefreshCallback = {}
            )
        }
    }
}
