package com.francescsoftware.weathersample.presentation.feature.weather

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    selectedCity: SelectedCity,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.onIntent(TodayMviIntent.Load(selectedCity))
    }
    val state = viewModel.state.collectAsState()
    WeatherScreen(
        state.value,
        selectedCity,
        viewModel,
        modifier
    )
}

@Composable
private fun WeatherScreen(
    state: TodayState,
    selectedCity: SelectedCity,
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
                selectedCity.name,
                selectedCity.countryCode
            ),
            style = MaterialTheme.typography.h4,
        )
        Spacer(modifier = Modifier.height(MarginQuad))
        WeatherSelector(
            selectedOption = state.option,
            onOptionSelect = { option -> weatherCallbacks.onOptionSelect(option) },
        )
        Spacer(modifier = Modifier.height(MarginQuad))
        Crossfade(
            targetState = state.loadState,
            modifier = Modifier.fillMaxSize()
        ) {
            when (state.loadState) {
                WeatherLoadState.IDLE -> {
                }
                WeatherLoadState.LOADING -> LoadingSpinner()
                WeatherLoadState.LOADED -> WeatherContent(state)
                WeatherLoadState.ERROR -> ErrorMessage(weatherCallbacks)
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
private fun WeatherContent(state: TodayState) {
    when (state.option) {
        WeatherSelectorOptions.Today -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                TodayWeatherCard(
                    state = state.todayState,
                    modifier = Modifier.fillMaxWidth(.85f),
                )
            }
        }
        WeatherSelectorOptions.Forecast -> LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(bottom = MarginSingle),
            verticalArrangement = Arrangement.spacedBy(MarginDouble),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.forecastItems) { item ->
                when (item) {
                    is ForecastItem.ForecastHeader -> ForecastHeader(
                        state = item,
                        modifier = Modifier.fillMaxWidth(.85f),
                    )
                    is ForecastItem.ForecastCard -> ForecastWeatherCard(
                        state = item,
                        modifier = Modifier
                            .fillMaxWidth(.85f)
                            .padding(horizontal = MarginSingle),
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorMessage(weatherCallbacks: WeatherCallbacks) {
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

@Preview(showBackground = true)
@Composable
private fun ForecastWeatherScreenPreview() {
    WeatherSampleTheme {
        Surface(modifier = Modifier.width(420.dp)) {
            val state = TodayState(
                loadState = WeatherLoadState.LOADED,
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
                option = WeatherSelectorOptions.Forecast,
                errorMessage = ""
            )
            val city = SelectedCity(
                name = "Vancouver",
                country = "Canada",
                countryCode = "CA"
            )
            WeatherScreen(
                state = state,
                selectedCity = city,
                weatherCallbacks = object : WeatherCallbacks {
                    override fun onOptionSelect(
                        weatherSelectorOptions: WeatherSelectorOptions
                    ) = Unit

                    override fun retry() = Unit
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorWeatherScreenPreview() {
    WeatherSampleTheme {
        Surface(modifier = Modifier.width(420.dp)) {
            val state = TodayState(
                loadState = WeatherLoadState.ERROR,
                todayState = TodayWeatherCardState(),
                forecastItems = emptyList(),
                option = WeatherSelectorOptions.Forecast,
                errorMessage = ""
            )
            val city = SelectedCity(
                name = "Vancouver",
                country = "Canada",
                countryCode = "CA"
            )
            WeatherScreen(
                state = state,
                selectedCity = city,
                weatherCallbacks = object : WeatherCallbacks {
                    override fun onOptionSelect(
                        weatherSelectorOptions: WeatherSelectorOptions
                    ) = Unit

                    override fun retry() = Unit
                }
            )
        }
    }
}
