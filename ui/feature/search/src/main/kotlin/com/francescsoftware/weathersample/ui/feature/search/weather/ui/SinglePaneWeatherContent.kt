package com.francescsoftware.weathersample.ui.feature.search.weather.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.weather.presenter.WeatherScreen
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.LocalWindowSizeClass
import com.francescsoftware.weathersample.ui.shared.composable.common.extension.toRect
import com.francescsoftware.weathersample.ui.shared.composable.common.tools.plus
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.MultiSelector
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginQuad
import com.francescsoftware.weathersample.ui.shared.styles.PhoneDpSize
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import dev.chrisbanes.haze.haze
import kotlinx.collections.immutable.persistentListOf

private val OptionSelectorHeight = 40.dp

@Composable
internal fun SinglePaneWeatherContent(
    state: WeatherScreen.Weather.Loaded,
    refreshing: Boolean,
    todayRefreshCallback: () -> Unit,
    modifier: Modifier = Modifier,
    stateHolder: WeatherStateHolder = rememberWeatherStateHolder(),
) {
    var headerSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val density = LocalDensity.current
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .onPlaced { layoutCoordinates ->
                    headerSize = layoutCoordinates.size
                }
                .padding(bottom = MarginDouble),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val todayLabel = stringResource(id = R.string.today_weather_button_label)
            val forecastLabel = stringResource(id = R.string.forecast_weather_button_label)
            Spacer(modifier = Modifier.height(MarginDouble))
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
                    refreshing = refreshing,
                    todayRefreshCallback = todayRefreshCallback,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = with(density) { headerSize.height.toDp() } + MarginQuad),
                )

                SelectedWeatherOption.Forecast -> WeatherForecast(
                    state = state,
                    contentPadding = WindowInsets.safeDrawing.asPaddingValues() +
                        PaddingValues(top = with(density) { headerSize.height.toDp() }),
                    modifier = Modifier
                        .fillMaxSize()
                        .haze(
                            headerSize.toRect(),
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            tint = MaterialTheme.colorScheme.surface.copy(alpha = .5f),
                            blurRadius = 16.dp,
                        )
                        .padding(horizontal = MarginDouble),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@PhonePreviews
@Composable
private fun PreviewPhoneWeatherContent(
    @PreviewParameter(LoadedWeatherStateWrapperProvider::class) stateWrapper: LoadedWeatherStateWrapper,
) {
    WeatherSampleTheme {
        CompositionLocalProvider(
            LocalWindowSizeClass provides WindowSizeClass.calculateFromSize(PhoneDpSize),
        ) {
            val stateHolder = rememberWeatherStateHolder(stateWrapper.option)
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                SinglePaneWeatherContent(
                    state = stateWrapper.state,
                    refreshing = false,
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
}
