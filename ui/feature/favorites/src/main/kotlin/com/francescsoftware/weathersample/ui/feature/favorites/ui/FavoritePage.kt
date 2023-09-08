package com.francescsoftware.weathersample.ui.feature.favorites.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.zIndex
import com.francescsoftware.weathersample.ui.shared.composable.common.tools.plus
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeader
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastWeather
import com.francescsoftware.weathersample.ui.shared.composable.weather.TodayWeatherCard
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginQuad
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlinx.collections.immutable.ImmutableList

private const val GradientStart = 0f
private const val GradientEnd = 1f
private const val GradientMidPoint = (GradientEnd - GradientStart) / 2f

@Immutable
internal data class FavoriteCardState(
    val city: City,
    val current: CurrentWeatherState,
    val forecast: ImmutableList<ForecastDayState>,
)

@Immutable
internal data class City(
    val favoriteId: Int,
    val name: String,
    val countryCode: String,
)

@Immutable
internal data class ForecastDayState(
    val header: ForecastHeaderState,
    val forecast: ImmutableList<ForecastHourState>,
)

@Composable
internal fun FavoritePage(
    state: FavoriteCardState,
    onDeleteClick: (City) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var offset by rememberSaveable {
        mutableIntStateOf(0)
    }
    val offsetDp = with(density) { offset.toDp() }
    Box(
        modifier = modifier,
    ) {
        CityNameLabel(
            name = state.city.name,
            countryCode = state.city.countryCode,
            onDeleteClick = {
                onDeleteClick(state.city)
            },
            modifier = Modifier
                .onPlaced {
                    offset = it.size.height
                }
                .zIndex(1f)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        GradientStart to MaterialTheme.colorScheme.surface,
                        GradientMidPoint to MaterialTheme.colorScheme.surface,
                        GradientEnd to Color.Transparent,
                    ),
                )
                .padding(bottom = MarginQuad),
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MarginDouble),
            contentPadding = WindowInsets.safeDrawing.only(
                WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal,
            ).asPaddingValues() + PaddingValues(top = offsetDp, bottom = MarginDouble),
        ) {
            item(
                key = "divider1",
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MarginDouble),
                )
            }
            item(
                key = "current_label",
            ) {
                WeatherLabel(
                    label = "Current Weather",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item(
                key = "current",
            ) {
                TodayWeatherCard(
                    state = state.current,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item(
                key = "divider2",
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MarginDouble),
                )
            }
            item(
                key = "forecast_label",
            ) {
                WeatherLabel(
                    label = "Forecast",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            state.forecast.forEach { dayForecast ->
                item(
                    key = dayForecast.header.date.toString(),
                ) {
                    ForecastHeader(
                        state = dayForecast.header,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                items(
                    items = dayForecast.forecast,
                    key = { forecast -> forecast.id },
                ) { hourForecast ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        ForecastWeather(
                            state = hourForecast,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = MarginDouble,
                                    end = MarginDouble,
                                    top = MarginDouble,
                                ),
                        )
                    }
                }
            }
        }
    }
}

@PhonePreviews
@Composable
private fun PreviewFavoriteCard() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            FavoritePage(
                state = VancouverFavoriteCardState,
                onDeleteClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginSingle),
            )
        }
    }
}
