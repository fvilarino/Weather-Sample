package com.francescsoftware.weathersample.ui.feature.search.city.presenter

import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.SelectedCity
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.parcelize.Parcelize

@Parcelize
object SearchScreen : Screen {
    sealed interface CitiesResult {
        data object Idle : CitiesResult
        data object Loading : CitiesResult
        data object NoResult : CitiesResult
        data object LoadError : CitiesResult

        data class CitiesLoaded(val cities: ImmutableList<CityResultModel>) : CitiesResult
    }

    data class State(
        val citiesResult: CitiesResult,
        val recentCities: ImmutableList<RecentCityModel>,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class QueryUpdated(val query: TextFieldValue) : Event
        data class CityClick(val city: SelectedCity) : Event
        data class ChipClick(val city: RecentCityModel) : Event
        data class DeleteChipClick(val city: RecentCityModel) : Event
        data class FavoriteClick(val city: CityResultModel) : Event
    }
}
