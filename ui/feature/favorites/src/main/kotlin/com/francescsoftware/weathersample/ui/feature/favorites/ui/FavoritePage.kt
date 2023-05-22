package com.francescsoftware.weathersample.ui.feature.favorites.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
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
    Column(
        modifier = modifier,
    ) {
        CityNameLabel(
            name = state.city.name,
            countryCode = state.city.countryCode,
            onDeleteClick = {
                onDeleteClick(state.city)
            },
            modifier = Modifier.fillMaxWidth(),
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(MarginDouble),
            contentPadding = PaddingValues(vertical = MarginDouble),
        ) {
            item(
                key = "divider1"
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MarginDouble),
                )
            }
            item(
                key = "current_label"
            ) {
                WeatherLabel(
                    label = "Current Weather",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item(
                key = "current"
            ) {
                TodayWeatherCard(
                    state = state.current,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item(
                key = "divider2"
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MarginDouble),
                )
            }
            item(
                key = "forecast_label"
            ) {
                WeatherLabel(
                    label = "Forecast",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            state.forecast.forEach { dayForecast ->
                item(
                    key = dayForecast.header.date,
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
            item {
                Spacer(modifier = Modifier.height(MarginQuad))
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
