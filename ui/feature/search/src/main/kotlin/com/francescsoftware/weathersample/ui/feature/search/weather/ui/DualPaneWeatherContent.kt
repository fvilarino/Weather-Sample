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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.ui.shared.composable.common.DualPane
import com.francescsoftware.weathersample.ui.shared.composable.common.PanesOrientation
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.styles.LandscapePhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.TabletPreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme

@Composable
internal fun DualPaneWeatherContent(
    state: WeatherState,
    deviceClass: DeviceClass,
    todayRefreshCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DualPane(
        panesOrientation = PanesOrientation.horizontal(
            aspectRatio = if (deviceClass == DeviceClass.Expanded) .33f else .5f
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

@LandscapePhonePreviews
@Composable
private fun PreviewPhoneDualPaneWeatherContent(
    @PreviewParameter(
        provider = WeatherStateWrapperProvider::class,
        limit = 1,
    ) stateWrapper: WeatherStateWrapper,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            DualPaneWeatherContent(
                state = stateWrapper.state,
                deviceClass = DeviceClass.Compact,
                todayRefreshCallback = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
            )
        }
    }
}

@TabletPreviews
@Composable
private fun PreviewTableDualPaneWeatherContent(
    @PreviewParameter(
        provider = WeatherStateWrapperProvider::class,
        limit = 1,
    ) stateWrapper: WeatherStateWrapper,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            DualPaneWeatherContent(
                state = stateWrapper.state,
                deviceClass = DeviceClass.Expanded,
                todayRefreshCallback = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
            )
        }
    }
}
