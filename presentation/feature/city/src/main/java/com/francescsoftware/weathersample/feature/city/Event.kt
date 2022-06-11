package com.francescsoftware.weathersample.feature.city

import com.francescsoftware.weathersample.shared.mvi.Action
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

    companion object {
        val initial = CityState(
            loadState = LoadState.Idle,
            query = "",
            cities = emptyList(),
        )
    }
}

internal sealed interface CityAction : Action {
    data class PrefixUpdated(val prefix: String) : CityAction
    data class CitiesLoaded(val cities: List<CityResultModel>) : CityAction
    object ClearQuery : CityAction
    object Start : CityAction
    object Loading : CityAction
    object NoResults : CityAction
    object LoadError : CityAction
}
