package com.francescsoftware.weathersample.feature.city.viewmodel

import com.francescsoftware.weathersample.feature.city.CityAction
import com.francescsoftware.weathersample.feature.city.CityState
import com.francescsoftware.weathersample.feature.city.LoadState
import com.francescsoftware.weathersample.shared.mvi.Reducer
import javax.inject.Inject

internal class CityReducer @Inject constructor() : Reducer<CityState, CityAction> {

    override fun reduce(
        state: CityState,
        action: CityAction,
    ): CityState = when (action) {
        is CityAction.CitiesLoaded -> state.copy(
            loadState = LoadState.Loaded,
            cities = action.cities,
        )
        CityAction.LoadError -> state.copy(loadState = LoadState.Error)
        CityAction.NoResults -> state.copy(loadState = LoadState.NoResults)
        CityAction.Loading -> state.copy(loadState = LoadState.Loading)
        else -> state
    }
}
