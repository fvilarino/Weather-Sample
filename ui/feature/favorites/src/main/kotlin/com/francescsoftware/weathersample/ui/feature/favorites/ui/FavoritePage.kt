package com.francescsoftware.weathersample.ui.feature.favorites.ui

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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.francescsoftware.weathersample.ui.feature.favorites.R
import com.francescsoftware.weathersample.ui.feature.favorites.presenter.FavoritesScreen
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
import dev.chrisbanes.haze.haze
import kotlinx.collections.immutable.ImmutableList

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
    state: FavoritesScreen.FavoriteCardState,
    onDeleteClick: (FavoritesScreen.City) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var headerWidth by rememberSaveable {
        mutableFloatStateOf(0f)
    }
    var headerHeight by rememberSaveable {
        mutableFloatStateOf(0f)
    }
    val headerHeightDp = with(density) { headerHeight.toDp() }
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
                    headerWidth = it.size.width.toFloat()
                    headerHeight = it.size.height.toFloat()
                }
                .fillMaxWidth()
                .padding(bottom = MarginQuad)
                .zIndex(1f),
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    Rect(
                        left = 0f,
                        top = 0f,
                        right = headerWidth,
                        bottom = headerHeight,
                    ),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    tint = MaterialTheme.colorScheme.surface.copy(alpha = .5f),
                    blurRadius = 16.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(MarginDouble),
            contentPadding = WindowInsets.safeDrawing.only(
                WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal,
            ).asPaddingValues() + PaddingValues(top = headerHeightDp, bottom = MarginDouble),
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
                    label = stringResource(R.string.current_weather),
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
                    label = stringResource(R.string.forecast),
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
