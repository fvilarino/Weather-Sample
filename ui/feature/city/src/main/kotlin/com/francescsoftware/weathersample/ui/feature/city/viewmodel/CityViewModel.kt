package com.francescsoftware.weathersample.ui.feature.city.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.core.coroutines.CloseableCoroutineScope
import com.francescsoftware.weathersample.ui.feature.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.shared.mvi.MviViewModel
import com.francescsoftware.weathersample.ui.shared.route.SelectedCity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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

    fun onQueryFocused() {
        handleAction(CityAction.QueryFocused)
    }

    fun onCityClick(selectedCity: SelectedCity) {
        handleAction(CityAction.OnCityClick(RecentCityModel(name = selectedCity.name)))
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
}
