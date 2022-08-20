package com.francescsoftware.weathersample.feature.weather

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherViewModel
import com.francescsoftware.weathersample.shared.composable.LoadingSpinner
import com.francescsoftware.weathersample.shared.composable.MultiSelector
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun WeatherScreen(
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    WeatherScreen(
        state = state,
        onOptionSelect = viewModel::onOptionSelect,
        onRefreshTodayWeather = viewModel::refreshTodayWeather,
        onRetry = viewModel::retry,
        modifier = modifier,
    )
}

@Composable
private fun WeatherScreen(
    state: WeatherState,
    onOptionSelect: (SelectedWeatherScreen) -> Unit,
    onRefreshTodayWeather: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val todayLabel = stringResource(id = R.string.today_weather_button_label)
    val forecastLabel = stringResource(id = R.string.forecast_weather_button_label)
    Column(
        modifier = modifier,
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
        MultiSelector(
            options = listOf(
                todayLabel,
                forecastLabel,
            ),
            selectedOption = when (state.option) {
                SelectedWeatherScreen.Today -> todayLabel
                SelectedWeatherScreen.Forecast -> forecastLabel
            },
            selectedColor = MaterialTheme.colors.onSecondary,
            selectedBackgroundColor = MaterialTheme.colors.secondary,
            onOptionSelect = { option ->
                if (option == todayLabel) {
                    onOptionSelect(SelectedWeatherScreen.Today)
                } else {
                    onOptionSelect(SelectedWeatherScreen.Forecast)
                }
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
                WeatherLoadState.Idle -> {
                }
                WeatherLoadState.Loading -> LoadingSpinner(
                    modifier = Modifier.fillMaxSize(),
                )
                WeatherLoadState.Loaded,
                WeatherLoadState.Refreshing -> WeatherContent(
                    state = state,
                    todayRefreshCallback = onRefreshTodayWeather,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MarginDouble),
                )
                WeatherLoadState.Error -> WeatherError(
                    modifier = Modifier.fillMaxSize(),
                    retry = onRetry,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 420)
@Preview(device = Devices.PIXEL_C)
@Composable
private fun ForecastWeatherScreenPreview(
    @PreviewParameter(WeatherStateProvider::class) state: WeatherState,
) {
    WeatherSampleTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            WeatherScreen(
                state = state,
                onOptionSelect = {},
                onRefreshTodayWeather = {},
                onRetry = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
            )
        }
    }
}
