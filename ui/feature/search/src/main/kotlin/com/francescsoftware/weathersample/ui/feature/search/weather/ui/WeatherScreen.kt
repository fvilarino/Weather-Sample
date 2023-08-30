package com.francescsoftware.weathersample.ui.feature.search.weather.ui

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel.WeatherLoadState
import com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel.WeatherViewModel
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.LoadingSpinner
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.TabletPreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme

@Composable
internal fun WeatherScreen(
    viewModel: WeatherViewModel,
    deviceClass: DeviceClass,
    modifier: Modifier = Modifier,
) {
    WeatherScreen(
        state = viewModel.state,
        deviceClass = deviceClass,
        onRefreshTodayWeather = viewModel::refreshTodayWeather,
        onRetry = viewModel::retry,
        modifier = modifier,
    )
}

@Composable
private fun WeatherScreen(
    state: WeatherState,
    deviceClass: DeviceClass,
    onRefreshTodayWeather: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = state.loadState,
        modifier = modifier,
        label = "weatherContent",
    ) { loadState ->
        when (loadState) {
            WeatherLoadState.Idle -> {
            }

            WeatherLoadState.Loading -> LoadingSpinner(
                modifier = Modifier.fillMaxSize(),
            )

            WeatherLoadState.Loaded,
            WeatherLoadState.Refreshing ->
                if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    DualPaneWeatherContent(
                        state = state,
                        deviceClass = deviceClass,
                        todayRefreshCallback = onRefreshTodayWeather,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = MarginDouble),
                    )
                } else {
                    SinglePaneWeatherContent(
                        state = state,
                        todayRefreshCallback = onRefreshTodayWeather,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = MarginDouble, start = MarginDouble, end = MarginDouble),
                    )
                }

            WeatherLoadState.Error -> WeatherError(
                message = state.errorMessage,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MarginDouble),
                retry = onRetry,
            )
        }
    }
}

@PhonePreviews
@Composable
private fun PreviewPhoneForecastWeatherScreen(
    @PreviewParameter(
        provider = WeatherStateWrapperProvider::class,
    ) weatherStateWrapper: WeatherStateWrapper,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            WeatherScreen(
                state = weatherStateWrapper.state,
                deviceClass = DeviceClass.Compact,
                onRefreshTodayWeather = {},
                onRetry = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
            )
        }
    }
}

@TabletPreviews
@Composable
private fun PreviewTabletForecastWeatherScreen(
    @PreviewParameter(
        provider = WeatherStateWrapperProvider::class,
    ) weatherStateWrapper: WeatherStateWrapper,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            WeatherScreen(
                state = weatherStateWrapper.state,
                deviceClass = DeviceClass.Expanded,
                onRefreshTodayWeather = {},
                onRetry = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
            )
        }
    }
}
