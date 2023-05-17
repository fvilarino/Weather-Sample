package com.francescsoftware.weathersample.ui.feature.search.weather.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.ui.shared.composable.common.MultiSelector
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginQuad
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlinx.collections.immutable.persistentListOf

private val OptionSelectorHeight = 40.dp

@Composable
internal fun SinglePaneWeatherContent(
    state: WeatherState,
    todayRefreshCallback: () -> Unit,
    modifier: Modifier = Modifier,
    stateHolder: WeatherStateHolder = rememberWeatherStateHolder(),
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val todayLabel = stringResource(id = R.string.today_weather_button_label)
        val forecastLabel = stringResource(id = R.string.forecast_weather_button_label)
        CityLabel(
            cityName = state.cityName,
            countryCode = state.cityCountryCode,
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
                .height(OptionSelectorHeight),
        )
        Crossfade(
            targetState = stateHolder.option,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = "option",
        ) { option ->
            when (option) {
                SelectedWeatherOption.Today -> TodayWeather(
                    state = state,
                    todayRefreshCallback = todayRefreshCallback,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MarginQuad),
                )

                SelectedWeatherOption.Forecast -> WeatherForecast(
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = MarginDouble),
                )
            }
        }
    }
}

@PhonePreviews
@Composable
private fun PreviewPhoneWeatherContent(
    @PreviewParameter(WeatherStateWrapperProvider::class) stateWrapper: WeatherStateWrapper,
) {
    WeatherSampleTheme {
        val stateHolder = rememberWeatherStateHolder(stateWrapper.option)
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            SinglePaneWeatherContent(
                state = stateWrapper.state,
                todayRefreshCallback = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
                stateHolder = stateHolder,
            )
        }
    }
}
