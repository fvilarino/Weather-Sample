package com.francescsoftware.weathersample.presentation.feature.search

import com.francescsoftware.weathersample.interactor.city.api.City
import com.francescsoftware.weathersample.mvi.Event
import com.francescsoftware.weathersample.mvi.MviIntent
import com.francescsoftware.weathersample.mvi.ReduceAction
import com.francescsoftware.weathersample.mvi.State

enum class LoadState {
    IDLE,
    LOADING,
    LOADED,
    NO_RESULTS,
    ERROR
}

data class CityState(
    val loadState: LoadState,
    val query: String,
    val cities: List<CityResultModel>,
) : State {
    val loading = loadState == LoadState.LOADING
    val loaded = loadState == LoadState.LOADED
    val noResults = loadState == LoadState.NO_RESULTS

    companion object {
        val initial = CityState(
            loadState = LoadState.IDLE,
            query = "",
            cities = emptyList(),
        )
    }
}

sealed interface CityEvent : Event

sealed interface CityMviIntent : MviIntent {
    data class PrefixUpdated(val prefix: String) : CityMviIntent
    data class CitiesLoaded(val cities: List<City>) : CityMviIntent
    object NoResults : CityMviIntent
    object LoadError : CityMviIntent
}

sealed interface CityReduceAction : ReduceAction {
    object Loading : CityReduceAction
    data class PrefixUpdated(val prefix: String) : CityReduceAction
    data class Loaded(val cities: List<CityResultModel>) : CityReduceAction
    object NoResults : CityReduceAction
    object LoadError : CityReduceAction
}
