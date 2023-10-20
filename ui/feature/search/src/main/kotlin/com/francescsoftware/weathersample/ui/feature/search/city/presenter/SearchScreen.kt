package com.francescsoftware.weathersample.ui.feature.search.city.presenter

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.compose.LazyPagingItems
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.parcelize.Parcelize

/** Search screen contract */
@Parcelize
object SearchScreen : Screen {

    @Immutable
    data class State(
        val showResults: Boolean,
        val cities: LazyPagingItems<City>,
        val favoriteCities: ImmutableSet<Long>,
        val recentCities: ImmutableList<RecentCityModel>,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data object RetryClick : Event
        data class QueryUpdated(val query: TextFieldValue) : Event
        data class CityClick(val city: City) : Event
        data class ChipClick(val city: RecentCityModel) : Event
        data class DeleteChipClick(val city: RecentCityModel) : Event
        data class FavoriteClick(val city: City) : Event
    }
}
