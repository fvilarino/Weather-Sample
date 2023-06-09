package com.francescsoftware.weathersample.ui.feature.search.city.viewmodel

import com.francescsoftware.weathersample.ui.shared.mvi.Reducer
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class CityReducer @Inject constructor() : Reducer<CityState, CityAction> {

    override fun reduce(
        state: CityState,
        action: CityAction,
    ): CityState = when (action) {
        is CityAction.QueryUpdated -> state.copy(
            loadState = if (action.query.text.isEmpty()) LoadState.Idle else state.loadState,
        )

        is CityAction.CitiesLoaded -> state.copy(
            loadState = LoadState.Loaded,
            cities = action.cities.toImmutableList(),
        )

        is CityAction.RecentCitiesLoaded -> state.copy(
            recentCities = action.recentCities.toImmutableList(),
            showRecentCities = true,
        )

        CityAction.HideRecentCities -> state.copy(showRecentCities = false)
        CityAction.LoadError -> state.copy(loadState = LoadState.Error)
        CityAction.NoResults -> state.copy(loadState = LoadState.NoResults)
        CityAction.Loading -> state.copy(loadState = LoadState.Loading)
        else -> state
    }
}
