package com.francescsoftware.weathersample.ui.feature.favorites.presenter

import androidx.compose.runtime.Stable
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.francescsoftware.weathersample.ui.shared.deeplink.LinkableDestination
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.squareup.anvil.annotations.ContributesMultibinding
import dagger.multibindings.StringKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.parcelize.Parcelize

private const val DeeplinkFavoritesHost = "favorite"

@Parcelize
@ContributesMultibinding(scope = ActivityScope::class, boundType = LinkableDestination::class)
@StringKey(DeeplinkFavoritesHost)
object FavoritesScreen : Screen, LinkableDestination {
    override fun parse(
        segments: List<String>,
        queryParams: Map<String, String>,
    ): List<Screen> = listOf(FavoritesScreen)

    data class City(
        val cityId: Long,
        val name: String,
        val countryCode: String,
    )

    data class FavoriteCardState(
        val city: City,
        val current: CurrentWeatherState,
        val forecast: ImmutableList<ForecastDayState>,
    )

    data class ForecastDayState(
        val header: ForecastHeaderState,
        val forecast: ImmutableList<ForecastHourState>,
    )

    data class FavoritePagerState(
        val pages: ImmutableList<FavoriteCardState>,
    )

    sealed interface FavoritesState {
        data object Loading : FavoritesState
        data object NoFavorites : FavoritesState
        data object Error : FavoritesState
        data class Loaded(
            val pagerState: FavoritePagerState,
        ) : FavoritesState
    }

    @Stable
    data class State(
        val favorites: FavoritesState,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class DeleteFavorite(val city: FavoritesScreen.City) : Event
    }
}
