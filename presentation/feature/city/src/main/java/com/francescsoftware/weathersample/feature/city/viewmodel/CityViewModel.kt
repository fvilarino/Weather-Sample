package com.francescsoftware.weathersample.feature.city.viewmodel

import com.francescsoftware.weathersample.feature.city.CityAction
import com.francescsoftware.weathersample.feature.city.CityState
import com.francescsoftware.weathersample.shared.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "CityViewModel"

@HiltViewModel
internal class CityViewModel @Inject constructor(
    reducer: CityReducer,
    middleware: CityMiddleware,
) : MviViewModel<CityState, CityAction>(
    reducer = reducer,
    middlewares = listOf(middleware),
    initialState = CityState.initial,
) {

    init {
        handleAction(CityAction.Start)
    }

    fun onQueryChange(query: String) {
        handleAction(CityAction.PrefixUpdated(query))
    }

    fun onClearQuery() {
        handleAction(CityAction.ClearQuery)
    }
}
