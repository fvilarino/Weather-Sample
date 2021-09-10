package com.francescsoftware.weathersample.presentation.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.weather.ForecastHeader
import com.francescsoftware.weathersample.presentation.feature.weather.ForecastItem
import com.francescsoftware.weathersample.presentation.feature.weather.ForecastWeatherCard
import com.francescsoftware.weathersample.presentation.feature.weather.TodayState
import com.francescsoftware.weathersample.presentation.feature.weather.TodayWeatherCard
import com.francescsoftware.weathersample.presentation.feature.weather.WeatherSelectorOptions
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.MarginSingle

private val WeatherCardWidth = 420.dp

@Composable
internal fun WeatherContent(
    state: TodayState,
    todayRefreshCallback: () -> Unit,
) {
    when (state.option) {
        WeatherSelectorOptions.Today -> TodayWeather(state, todayRefreshCallback)
        WeatherSelectorOptions.Forecast -> WeatherForecast(state)
    }
}

@Composable
private fun TodayWeather(
    state: TodayState,
    todayRefreshCallback: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MarginDouble),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TodayWeatherCard(
            state = state.todayState,
            modifier = Modifier.width(WeatherCardWidth),
        )
        OutlinedButton(
            onClick = todayRefreshCallback,
            modifier = Modifier.padding(top = MarginQuad),
        ) {
            Text(text = stringResource(id = R.string.refresh))
        }
    }
}

@Composable
private fun WeatherForecast(state: TodayState) {
    val width = LocalContext.current.resources.displayMetrics.widthPixels
    val minColumnWidth = with(LocalDensity.current) { WeatherCardWidth.toPx() }
    val numColumns = ((width / minColumnWidth).toInt()).coerceAtLeast(1)
    val gridWidth = with(LocalDensity.current) { (numColumns * minColumnWidth).toDp() }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = MarginSingle)) {
        LazyColumn(
            modifier = Modifier
                .width(gridWidth)
                .padding(bottom = MarginSingle)
                .align(Alignment.TopCenter),
            contentPadding = PaddingValues(bottom = MarginSingle),
        ) {
            var index = 0
            while (index < state.forecastItems.size) {
                when (val item = state.forecastItems[index++]) {
                    is ForecastItem.ForecastHeader -> headerCard(item)
                    is ForecastItem.ForecastCard -> {
                        val forecastItems = mutableListOf(item)
                        while (index < state.forecastItems.size) {
                            val next = state.forecastItems[index]
                            if (next is ForecastItem.ForecastCard) {
                                forecastItems.add(next)
                                ++index
                            } else {
                                break
                            }
                        }
                        forecastCards(
                            forecastItems = forecastItems,
                            numColumns = numColumns,
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.headerCard(item: ForecastItem.ForecastHeader) {
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MarginSingle)
        ) {
            ForecastHeader(
                state = item,
                modifier = Modifier
                    .width(WeatherCardWidth)
                    .align(Alignment.TopCenter),
            )
        }
    }
}

private fun LazyListScope.forecastCards(
    forecastItems: List<ForecastItem.ForecastCard>,
    numColumns: Int,
) {
    val rows = forecastItems.chunked(numColumns)
    rows.forEach { row ->
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MarginSingle)
            ) {
                row.forEach { state ->
                    ForecastWeatherCard(
                        state = state,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = MarginSingle),
                    )
                }
                if (row.size < numColumns) {
                    repeat(numColumns - row.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
