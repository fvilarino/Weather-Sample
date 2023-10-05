package com.francescsoftware.weathersample.ui.feature.search.weather.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.weather.presenter.WeatherScreen
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.Crossfade
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.LoadingSpinner
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.slack.circuit.codegen.annotations.CircuitInject

@CircuitInject(WeatherScreen::class, ActivityScope::class)
@Composable
internal fun Weather(
    state: WeatherScreen.State,
    modifier: Modifier = Modifier,
) {
    WeatherScreen(
        state = state,
        onRefreshTodayWeather = { state.eventSink(WeatherScreen.Event.RefreshClick) },
        onRetry = { state.eventSink(WeatherScreen.Event.RetryClick) },
        modifier = modifier,
    )
}

@Composable
private fun WeatherScreen(
    state: WeatherScreen.State,
    onRefreshTodayWeather: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = state.weather,
        contentKey = { it::class.java },
        modifier = modifier,
        label = "weatherContent",
    ) { weatherState ->
        when (weatherState) {
            WeatherScreen.Weather.Loading -> LoadingSpinner(
                modifier = Modifier.fillMaxSize(),
            )

            is WeatherScreen.Weather.Error -> WeatherError(
                message = stringResource(id = R.string.failed_to_load_weather_data),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MarginDouble),
                retry = onRetry,
            )

            is WeatherScreen.Weather.Loaded -> if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                DualPaneWeatherContent(
                    state = weatherState,
                    refreshing = state.refreshing,
                    todayRefreshCallback = onRefreshTodayWeather,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MarginDouble),
                )
            } else {
                SinglePaneWeatherContent(
                    state = weatherState,
                    refreshing = state.refreshing,
                    todayRefreshCallback = onRefreshTodayWeather,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = MarginDouble, start = MarginDouble, end = MarginDouble),
                )
            }
        }
    }
}
/*

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
*/
