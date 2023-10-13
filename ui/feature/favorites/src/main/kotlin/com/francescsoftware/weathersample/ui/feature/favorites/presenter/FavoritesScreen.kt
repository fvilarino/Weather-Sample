package com.francescsoftware.weathersample.ui.feature.favorites.presenter

import androidx.compose.runtime.Immutable
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.parcelize.Parcelize

@Parcelize
object FavoritesScreen : Screen {

    data class City(
        val favoriteId: Int,
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

    @Immutable
    data class State(
        val favorites: FavoritesState,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class DeleteFavorite(val city: FavoritesScreen.City) : Event
    }
}
