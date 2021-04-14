package com.francescsoftware.weathersample.presentation.feature.search

import com.francescsoftware.weathersample.presentation.shared.mvi.Event
import com.francescsoftware.weathersample.presentation.shared.mvi.MviIntent
import com.francescsoftware.weathersample.presentation.shared.mvi.ReduceAction
import com.francescsoftware.weathersample.presentation.shared.mvi.State

enum class LoadState {
    IDLE,
    LOADING,
    LOADED,
    NO_RESULTS,
    ERROR
}

data class CityState(
    val loadState: LoadState,
    val cities: List<CityResultModel>,
) : State {
    val loading = loadState == LoadState.LOADING
    val loaded = loadState == LoadState.LOADED
    val noResults = loadState == LoadState.NO_RESULTS

    companion object {
        val initial = CityState(
            loadState = LoadState.IDLE,
            cities = emptyList()
        )
    }
}

sealed class CityEvent : Event {
    data class ShowSnackBar(val message: CharSequence) : CityEvent()
}

sealed class CityMviIntent : MviIntent {
    data class PrefixUpdated(val prefix: String) : CityMviIntent()
}

sealed class CityReduceAction : ReduceAction {
    object Loading : CityReduceAction()
    data class Loaded(val cities: List<CityResultModel>) : CityReduceAction()
    object NoResults : CityReduceAction()
    object LoadError : CityReduceAction()
}
