package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherLoadState
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherViewModel
import com.francescsoftware.weathersample.shared.composable.LoadingSpinner
import com.francescsoftware.weathersample.shared.composable.MultiSelector
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.PhonePreviews
import com.francescsoftware.weathersample.styles.TabletPreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun WeatherScreen(
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WeatherScreen(
        state = state,
        onRefreshTodayWeather = viewModel::refreshTodayWeather,
        onRetry = viewModel::retry,
        modifier = modifier,
    )
}

@Composable
private fun WeatherScreen(
    state: WeatherState,
    onRefreshTodayWeather: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    stateHolder: WeatherStateHolder = rememberWeatherStateHolder(),
) {
    val todayLabel = stringResource(id = R.string.today_weather_button_label)
    val forecastLabel = stringResource(id = R.string.forecast_weather_button_label)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.weather_city_label),
            style = MaterialTheme.typography.bodyLarge,
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
            style = MaterialTheme.typography.headlineSmall,
        )
        MultiSelector(
            options = persistentListOf(
                todayLabel,
                forecastLabel,
            ),
            selectedOption = when (stateHolder.option) {
                SelectedWeatherOption.Today -> todayLabel
                SelectedWeatherOption.Forecast -> forecastLabel
            },
            selectedColor = MaterialTheme.colorScheme.onSecondary,
            selectedBackgroundColor = MaterialTheme.colorScheme.secondary,
            onOptionSelect = { option ->
                if (option == todayLabel) {
                    stateHolder.onOptionSelect(SelectedWeatherOption.Today)
                } else {
                    stateHolder.onOptionSelect(SelectedWeatherOption.Forecast)
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
                    option = stateHolder.option,
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

@PhonePreviews
@TabletPreviews
@Composable
private fun ForecastWeatherScreenPreview(
    @PreviewParameter(WeatherStateWrapperProvider::class) weatherStateWrapper: WeatherStateWrapper,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val holder = rememberWeatherStateHolder()
            holder.onOptionSelect(weatherStateWrapper.option)
            WeatherScreen(
                state = weatherStateWrapper.state,
                stateHolder = holder,
                onRefreshTodayWeather = {},
                onRetry = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
            )
        }
    }
}
