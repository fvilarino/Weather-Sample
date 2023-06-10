package com.francescsoftware.weathersample.ui.feature.search.city.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.core.coroutines.CloseableCoroutineScope
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.navigation.SelectedCity
import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
import com.francescsoftware.weathersample.ui.shared.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CityViewModel @Inject constructor(
    closeableScope: CloseableCoroutineScope,
    reducer: CityReducer,
    middlewares: Set<@JvmSuppressWildcards Middleware<CityState, CityAction>>,
) : MviViewModel<CityState, CityAction>(
    closeableScope = closeableScope,
    reducer = reducer,
    middlewares = middlewares.toList(),
    initialState = CityState.initial,
) {

    init {
        handleAction(CityAction.Start)
    }

    fun onQueryChange(query: TextFieldValue) {
        handleAction(CityAction.QueryUpdated(query))
    }

    fun onQueryFocused() {
        handleAction(CityAction.QueryFocused)
    }

    fun onCityClick(selectedCity: SelectedCity) {
        handleAction(CityAction.OnCityClick(selectedCity))
    }

    fun onFavoriteClick(cityResultModel: CityResultModel) {
        handleAction(CityAction.OnFavoriteClick(city = cityResultModel))
    }

    fun onChipClick(recent: RecentCityModel) {
        handleAction(CityAction.OnChipClick(recent))
    }

    fun onDeleteChip(recent: RecentCityModel) {
        handleAction(CityAction.OnDeleteChipClick(recent))
    }

    fun onNavigated() {
        handleAction(CityAction.OnNavigated)
    }
}
