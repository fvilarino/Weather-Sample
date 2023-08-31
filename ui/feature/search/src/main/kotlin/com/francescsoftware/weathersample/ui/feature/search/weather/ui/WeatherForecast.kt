package com.francescsoftware.weathersample.ui.feature.search.weather.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeader
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme

@Composable
internal fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(
            minSize = WeatherCardWidth,
        ),
        modifier = modifier,
        contentPadding = contentPadding,
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
