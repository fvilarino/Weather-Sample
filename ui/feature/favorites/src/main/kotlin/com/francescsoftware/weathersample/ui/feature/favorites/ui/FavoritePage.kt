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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.francescsoftware.weathersample.ui.feature.favorites.R
import com.francescsoftware.weathersample.ui.feature.favorites.presenter.FavoritesScreen
import com.francescsoftware.weathersample.ui.shared.composable.common.saver.intSizeSaver
import com.francescsoftware.weathersample.ui.shared.composable.common.tools.plus
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeader
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastWeather
import com.francescsoftware.weathersample.ui.shared.composable.weather.TodayWeatherCard
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
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
    var headerSize: IntSize by rememberSaveable(
        stateSaver = intSizeSaver(),
    ) {
        mutableStateOf(IntSize.Zero)
    }
    val hazeState = remember { HazeState() }
    val headerHeightDp = with(density) { headerSize.height.toDp() }
    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    state = hazeState,
                    style = HazeStyle(
                        tint = MaterialTheme.colorScheme.surface.copy(alpha = .5f),
                        blurRadius = 16.dp,
                        noiseFactor = HazeDefaults.noiseFactor,
                    ),
                ),
            verticalArrangement = Arrangement.spacedBy(MarginDouble),
            contentPadding = WindowInsets.safeDrawing.only(
                WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal,
            ).asPaddingValues() + PaddingValues(top = headerHeightDp, bottom = MarginDouble),
        ) {
            item(
                key = "divider1",
            ) {
                HorizontalDivider(
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
                HorizontalDivider(
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
                        HorizontalDivider(
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
        CityNameLabel(
            name = state.city.name,
            countryCode = state.city.countryCode,
            onDeleteClick = {
                onDeleteClick(state.city)
            },
            modifier = Modifier
                .hazeChild(hazeState)
                .onPlaced {
                    headerSize = it.size
                }
                .fillMaxWidth()
                .padding(vertical = MarginDouble)
                .zIndex(1f),
        )
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
