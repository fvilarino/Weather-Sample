package com.francescsoftware.weathersample.feature.city

import com.francescsoftware.weathersample.interactor.city.api.City
import com.francescsoftware.weathersample.shared.mvi.Event
import com.francescsoftware.weathersample.shared.mvi.MviIntent
import com.francescsoftware.weathersample.shared.mvi.ReduceAction
import com.francescsoftware.weathersample.shared.mvi.State

internal enum class LoadState {
    Idle,
    Loading,
    Loaded,
    NoResults,
    Error,
}

internal data class CityState(
    val loadState: LoadState,
    val query: String,
    val cities: List<CityResultModel>,
) : State {
    val loading = loadState == LoadState.Loading
    val loaded = loadState == LoadState.Loaded
    val noResults = loadState == LoadState.NoResults

    companion object {
        val initial = CityState(
            loadState = LoadState.Idle,
            query = "",
            cities = emptyList(),
        )
    }
}

internal sealed interface CityEvent : Event

internal sealed interface CityMviIntent : MviIntent {
    data class PrefixUpdated(val prefix: String) : CityMviIntent
    data class CitiesLoaded(val cities: List<City>) : CityMviIntent
    object NoResults : CityMviIntent
    object LoadError : CityMviIntent
}

internal sealed interface CityReduceAction : ReduceAction {
    object Loading : CityReduceAction
    data class PrefixUpdated(val prefix: String) : CityReduceAction
    data class Loaded(val cities: List<CityResultModel>) : CityReduceAction
    object NoResults : CityReduceAction
    object LoadError : CityReduceAction
}
