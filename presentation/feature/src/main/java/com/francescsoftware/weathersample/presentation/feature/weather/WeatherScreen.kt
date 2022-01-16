package com.francescsoftware.weathersample.presentation.feature.weather

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.search.LoadingSpinner
import com.francescsoftware.weathersample.presentation.feature.search.WeatherContent
import com.francescsoftware.weathersample.shared.composable.TwoOptionsSelector
import com.francescsoftware.weathersample.shared.composable.TwoOptionsSelectorOptions
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun WeatherScreen(
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
) {
    WeatherScreen(
        viewModel.state.value,
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
        TwoOptionsSelector(
            leftLabel = stringResource(id = R.string.today_weather_button_label),
            rightLabel = stringResource(id = R.string.forecast_weather_button_label),
            selectedOption = when (state.option) {
                SelectedWeatherScreen.Today -> TwoOptionsSelectorOptions.Left
                SelectedWeatherScreen.Forecast -> TwoOptionsSelectorOptions.Right
            },
            selectedColor = MaterialTheme.colors.secondary,
            deselectedColor = MaterialTheme.colors.surface,
            onOptionSelect = { option ->
                weatherCallbacks.onOptionSelect(
                    when (option) {
                        TwoOptionsSelectorOptions.Left -> SelectedWeatherScreen.Today
                        TwoOptionsSelectorOptions.Right -> SelectedWeatherScreen.Forecast
                    }
                )
            },
            modifier = Modifier
                .padding(top = MarginQuad)
                .height(40.dp),
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
                WeatherLoadState.LOADED,
                WeatherLoadState.REFRESHING -> WeatherContent(
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

private class WeatherCallbacksPreview : WeatherCallbacks {
    override fun onOptionSelect(selectedWeatherScreen: SelectedWeatherScreen) = Unit

    override fun refreshTodayWeather() = Unit

    override fun retry() = Unit
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
                    iconId = R.drawable.ic_partly_cloudy,
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
                option = SelectedWeatherScreen.Today,
                errorMessage = ""
            )
            WeatherScreen(
                state = state,
                weatherCallbacks = WeatherCallbacksPreview()
            )
        }
    }
}
