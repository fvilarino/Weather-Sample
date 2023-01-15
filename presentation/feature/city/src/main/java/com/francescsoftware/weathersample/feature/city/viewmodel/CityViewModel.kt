package com.francescsoftware.weathersample.feature.city.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.coroutines.CloseableCoroutineScope
import com.francescsoftware.weathersample.feature.city.model.RecentCityModel
import com.francescsoftware.weathersample.presentation.route.SelectedCity
import com.francescsoftware.weathersample.shared.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

internal const val MinCityLengthForSerch = 3
private const val TAG = "CityViewModel"

@HiltViewModel
internal class CityViewModel @Inject constructor(
    closeableScope: CloseableCoroutineScope,
    reducer: CityReducer,
    cityMiddleware: CityMiddleware,
    recentCitiesMiddleware: RecentCitiesMiddleware,
) : MviViewModel<CityState, CityAction>(
    closeableScope = closeableScope,
    reducer = reducer,
    middlewares = listOf(cityMiddleware, recentCitiesMiddleware),
    initialState = CityState.initial,
) {

    init {
        handleAction(CityAction.Start)
    }

    fun onQueryChange(query: TextFieldValue) {
        handleAction(CityAction.QueryUpdated(query))
    }

    fun onClearQuery() {
        handleAction(CityAction.ClearQuery)
    }

    fun onQueryFocused() {
        handleAction(CityAction.QueryFocused)
    }

    fun onCityClick(selectedCity: SelectedCity) {
        handleAction(CityAction.OnCityClick(RecentCityModel(name = selectedCity.name)))
    }

    fun onChipClick(recent: RecentCityModel) {
        handleAction(CityAction.OnChipClick(recent))
    }

    fun onDeleteChip(recent: RecentCityModel) {
        handleAction(CityAction.OnDeleteChipClick(recent))
    }
}
