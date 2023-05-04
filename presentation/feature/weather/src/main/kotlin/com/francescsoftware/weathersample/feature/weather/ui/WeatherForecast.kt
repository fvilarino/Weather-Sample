package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.PhonePreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun WeatherForecast(
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
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
    }
}

@PhonePreviews
@Composable
private fun PreviewWeatherForecast(
    @PreviewParameter(
        provider = WeatherStateWrapperProvider::class,
        limit = 1,
    ) stateWrapper: WeatherStateWrapper,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            WeatherForecast(
                state = stateWrapper.state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
            )
        }
    }
}
