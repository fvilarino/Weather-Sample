package com.francescsoftware.weathersample.feature.search

import com.francescsoftware.weathersample.feature.common.mvi.Event
import com.francescsoftware.weathersample.feature.common.mvi.MviIntent
import com.francescsoftware.weathersample.feature.common.mvi.ReduceAction
import com.francescsoftware.weathersample.feature.common.mvi.State

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

sealed class CityEvent : Event

sealed class CityMviIntent : MviIntent {
    data class PrefixUpdated(val prefix: String) : CityMviIntent()
}

sealed class CityReduceAction : ReduceAction {
    object Loading : CityReduceAction()
    data class Loaded(val cities: List<CityResultModel>) : CityReduceAction()
    object LoadError : CityReduceAction()
}
