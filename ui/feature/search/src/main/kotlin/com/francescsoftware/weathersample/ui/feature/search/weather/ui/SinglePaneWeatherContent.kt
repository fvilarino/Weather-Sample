package com.francescsoftware.weathersample.ui.feature.search.weather.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.ui.shared.composable.common.MultiSelector
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginQuad
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlinx.collections.immutable.persistentListOf

private val OptionSelectorHeight = 40.dp
private const val GradientStart = 0f
private const val GradientEnd = 1f
private const val GradientMidPoint = (GradientEnd - GradientStart) / 2f

@Composable
internal fun SinglePaneWeatherContent(
    state: WeatherState,
    todayRefreshCallback: () -> Unit,
    modifier: Modifier = Modifier,
    stateHolder: WeatherStateHolder = rememberWeatherStateHolder(),
) {
    var offset by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
                .height(offset)
                .background(
                    brush = Brush.verticalGradient(
                        GradientStart to MaterialTheme.colorScheme.surface,
                        GradientMidPoint to MaterialTheme.colorScheme.surface,
                        GradientEnd to Color.Transparent,
                    )
                )
        )
        Column(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .onPlaced { layoutCoordinates ->
                    offset = with(density) { layoutCoordinates.size.height.toDp() }
                }
                .padding(bottom = MarginDouble),
            horizontalAlignment = Alignment.CenterHorizontally,
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
                unselectedBackgroundColor = lerp(
                    start = MaterialTheme.colorScheme.primary,
                    stop = MaterialTheme.colorScheme.surface,
                    fraction = .8f,
                ),
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
        }
        Crossfade(
            targetState = stateHolder.option,
            modifier = Modifier.fillMaxSize(),
            label = "option",
        ) { option ->
            when (option) {
                SelectedWeatherOption.Today -> TodayWeather(
                    state = state,
                    todayRefreshCallback = todayRefreshCallback,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = offset + MarginQuad),
                )

                SelectedWeatherOption.Forecast -> WeatherForecast(
                    state = state,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = MarginDouble,
                        end = MarginDouble,
                        top = offset,
                        bottom = MarginDouble,
                    )
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
                    .padding(
                        top = MarginDouble,
                        start = MarginDouble,
                        end = MarginDouble,
                    ),
                stateHolder = stateHolder,
            )
        }
    }
}
