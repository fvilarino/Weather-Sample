package com.francescsoftware.weathersample.presentation.feature.search

import com.francescsoftware.weathersample.presentation.shared.mvi.Event
import com.francescsoftware.weathersample.presentation.shared.mvi.MviIntent
import com.francescsoftware.weathersample.presentation.shared.mvi.ReduceAction
import com.francescsoftware.weathersample.presentation.shared.mvi.State

data class CityState(
    val loading: Boolean,
    val cities: List<CityResultModel>,
) : State {
    companion object {
        val initial = CityState(
            loading = false,
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
    object LoadError : CityReduceAction()
}
