package com.francescsoftware.weathersample.ui.feature.search.city.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.core.coroutines.CloseableCoroutineScope
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.navigation.SelectedCity
import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
import com.francescsoftware.weathersample.ui.shared.mvi.MviViewModel
import javax.inject.Inject

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

    fun onStart() {
        dispatch(CityAction.Start)
    }

    fun onStop() {
        dispatch(CityAction.Stop)
    }

    fun onQueryChange(query: TextFieldValue) {
        dispatch(CityAction.QueryUpdated(query))
    }

    fun onQueryFocused() {
        dispatch(CityAction.QueryFocused)
    }

    fun onCityClick(selectedCity: SelectedCity) {
        dispatch(CityAction.OnCityClick(selectedCity))
    }

    fun onFavoriteClick(cityResultModel: CityResultModel) {
        dispatch(CityAction.OnFavoriteClick(city = cityResultModel))
    }

    fun onChipClick(recent: RecentCityModel) {
        dispatch(CityAction.OnChipClick(recent))
    }

    fun onDeleteChip(recent: RecentCityModel) {
        dispatch(CityAction.OnDeleteChipClick(recent))
    }

    fun onNavigated() {
        dispatch(CityAction.OnNavigated)
    }
}
