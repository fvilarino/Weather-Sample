package com.francescsoftware.weathersample.ui.feature.search.weather.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.ui.feature.search.weather.presenter.WeatherScreen
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.LocalWindowSizeClass
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.DualPane
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.PanesOrientation
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.styles.LandscapePhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.PhoneDpSize
import com.francescsoftware.weathersample.ui.shared.styles.TabletDpSize
import com.francescsoftware.weathersample.ui.shared.styles.TabletPreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme

private const val ExpandedDeviceRation = .33f
private const val DefaultDeviceRatio = .5f

@Composable
internal fun DualPaneWeatherContent(
    state: WeatherScreen.Weather.Loaded,
    refreshing: Boolean,
    todayRefreshCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowSizeClass = LocalWindowSizeClass.current
    val deviceClass = DeviceClass.fromWindowSizeClass(windowSizeClass = windowSizeClass)
    DualPane(
        panesOrientation = PanesOrientation.horizontal(
            aspectRatio = if (deviceClass == DeviceClass.Expanded) {
                ExpandedDeviceRation
            } else {
                DefaultDeviceRatio
            },
        ),
        paneOne = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = MarginSingle),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.verticalScroll(state = rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CityLabel(
                        cityName = state.cityName,
                        countryCode = state.cityCountryCode,
                        modifier = Modifier.padding(top = MarginDouble),
                    )
                    TodayWeather(
                        state = state,
                        refreshing = refreshing,
                        todayRefreshCallback = todayRefreshCallback,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = MarginDouble),
                    )
                }
            }
        },
        paneTwo = {
            WeatherForecast(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = MarginSingle),
            )
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@LandscapePhonePreviews
@Composable
private fun PreviewPhoneDualPaneWeatherContent(
    @PreviewParameter(
        provider = LoadedWeatherStateWrapperProvider::class,
        limit = 1,
    ) stateWrapper: LoadedWeatherStateWrapper,
) {
    WeatherSampleTheme {
        CompositionLocalProvider(
            LocalWindowSizeClass provides WindowSizeClass.calculateFromSize(PhoneDpSize),
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                DualPaneWeatherContent(
                    state = stateWrapper.state,
                    refreshing = false,
                    todayRefreshCallback = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = MarginDouble),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@TabletPreviews
@Composable
private fun PreviewTableDualPaneWeatherContent(
    @PreviewParameter(
        provider = LoadedWeatherStateWrapperProvider::class,
        limit = 1,
    ) stateWrapper: LoadedWeatherStateWrapper,
) {
    WeatherSampleTheme {
        CompositionLocalProvider(
            LocalWindowSizeClass provides WindowSizeClass.calculateFromSize(TabletDpSize),
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                DualPaneWeatherContent(
                    state = stateWrapper.state,
                    refreshing = false,
                    todayRefreshCallback = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = MarginDouble),
                )
            }
        }
    }
}
