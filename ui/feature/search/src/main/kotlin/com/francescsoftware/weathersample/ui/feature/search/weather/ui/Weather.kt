package com.francescsoftware.weathersample.ui.feature.search.weather.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.weather.presenter.WeatherScreen
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.LocalWindowSizeClass
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.Crossfade
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.LoadingSpinner
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.PhoneDpSize
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.TabletDpSize
import com.francescsoftware.weathersample.ui.shared.styles.TabletPreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.slack.circuit.codegen.annotations.CircuitInject

@CircuitInject(WeatherScreen::class, ActivityScope::class)
@Composable
internal fun Weather(
    state: WeatherScreen.State,
    modifier: Modifier = Modifier,
) {
    val eventSink = state.eventSink

    WeatherScreen(
        state = state,
        onRefreshTodayWeather = { eventSink(WeatherScreen.Event.RefreshClick) },
        onRetry = { eventSink(WeatherScreen.Event.RetryClick) },
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

            is WeatherScreen.Weather.Loaded -> if (
                LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) {
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
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@PhonePreviews
@Composable
private fun PreviewPhoneForecastWeatherScreen(
    @PreviewParameter(
        provider = WeatherStateProvider::class,
    ) state: WeatherScreen.State,
) {
    WeatherSampleTheme {
        CompositionLocalProvider(
            LocalWindowSizeClass provides WindowSizeClass.calculateFromSize(PhoneDpSize),
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                WeatherScreen(
                    state = state,
                    onRefreshTodayWeather = {},
                    onRetry = {},
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
private fun PreviewTabletForecastWeatherScreen(
    @PreviewParameter(
        provider = WeatherStateProvider::class,
    ) state: WeatherScreen.State,
) {
    WeatherSampleTheme {
        CompositionLocalProvider(
            LocalWindowSizeClass provides WindowSizeClass.calculateFromSize(TabletDpSize),
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                WeatherScreen(
                    state = state,
                    onRefreshTodayWeather = {},
                    onRetry = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = MarginDouble),
                )
            }
        }
    }
}
