package com.francescsoftware.weathersample.presentation.feature.weather

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

private val WeatherCardWidth = 420.dp

@Composable
internal fun WeatherScreen(
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.onIntent(TodayMviIntent.Load)
    }
    val state = viewModel.state.collectAsState()
    WeatherScreen(
        state.value,
        viewModel,
        modifier
    )
}

@Composable
private fun WeatherScreen(
    state: TodayState,
    weatherCallbacks: WeatherCallbacks,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.weather_city_label),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = MarginQuad),
        )
        Text(
            text = stringResource(
                id = R.string.weather_city_name,
                state.cityName,
                state.cityCountryCode,
            ),
            modifier = Modifier.padding(horizontal = MarginDouble),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4,
        )
        WeatherSelector(
            selectedOption = state.option,
            onOptionSelect = { option -> weatherCallbacks.onOptionSelect(option) },
            modifier = Modifier.padding(top = MarginQuad),
        )
        Crossfade(
            targetState = state.loadState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = MarginQuad),
        ) { loadState ->
            when (loadState) {
                WeatherLoadState.IDLE -> {
                }
                WeatherLoadState.LOADING -> LoadingSpinner()
                WeatherLoadState.LOADED -> WeatherContent(
                    state,
                    weatherCallbacks::refreshTodayWeather
                )
                WeatherLoadState.ERROR -> WeatherError(
                    modifier = Modifier.fillMaxSize(),
                    weatherCallbacks::retry
                )
            }
        }
    }
}

@Composable
private fun LoadingSpinner() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun WeatherContent(
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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 420)
@Preview(device = Devices.PIXEL_C)
@Composable
private fun ForecastWeatherScreenPreview() {
    WeatherSampleTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            val state = TodayState(
                loadState = WeatherLoadState.LOADED,
                cityName = "Coquitlam, British Columbia",
                cityCountryCode = "CA",
                todayState = TodayWeatherCardState(
                    temperature = "16.4°C",
                    minTemperature = "11.3°C",
                    maxTemperature = "22.7°C",
                    feelsLikeTemperature = "14.3°C",
                    description = "Partly cloudy",
                    iconId = com.francescsoftware.weathersample.presentation.shared.R.drawable.ic_partly_cloudy,
                    windSpeed = "4.3kph",
                    humidity = "54%",
                    pressure = "1024mb",
                    visibility = "10000 m"
                ),
                forecastItems = listOf(
                    ForecastItem.ForecastHeader(
                        id = 1L,
                        date = "Sun April 18",
                        sunrise = "06:23",
                        sunset = "20:01",
                    ),
                    ForecastItem.ForecastCard(
                        id = 1L,
                        header = "02:00 - Scattered Clouds",
                        iconId = R.drawable.ic_partly_cloudy,
                        minTemperature = "16.4°C",
                        maxTemperature = "23.7°C",
                        feelsLikeTemperature = "15.5°C",
                        windSpeed = "5.4 kph",
                        humidity = "48 %",
                        visibility = "10000 m",
                    ),
                    ForecastItem.ForecastCard(
                        id = 1L,
                        header = "02:00 - Scattered Clouds",
                        iconId = R.drawable.ic_partly_cloudy,
                        minTemperature = "16.4°C",
                        maxTemperature = "23.7°C",
                        feelsLikeTemperature = "15.5°C",
                        windSpeed = "5.4 kph",
                        humidity = "48 %",
                        visibility = "10000 m",
                    ),
                ),
                option = WeatherSelectorOptions.Today,
                errorMessage = ""
            )
            WeatherScreen(
                state = state,
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
