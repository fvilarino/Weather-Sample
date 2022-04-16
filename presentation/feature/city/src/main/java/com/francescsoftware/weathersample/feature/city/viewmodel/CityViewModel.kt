package com.francescsoftware.weathersample.feature.city.viewmodel

import com.francescsoftware.weathersample.feature.city.CityAction
import com.francescsoftware.weathersample.feature.city.CityCallbacks
import com.francescsoftware.weathersample.feature.city.CityResultModel
import com.francescsoftware.weathersample.feature.city.CityState
import com.francescsoftware.weathersample.feature.navigation.api.Navigator
import com.francescsoftware.weathersample.feature.navigation.api.SelectedCity
import com.francescsoftware.weathersample.shared.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "CityViewModel"

@HiltViewModel
internal class CityViewModel @Inject constructor(
    reducer: CityReducer,
    middleware: CityMiddleware,
    private val navigator: Navigator,
) : MviViewModel<CityState, CityAction>(
    reducer = reducer,
    middlewares = listOf(middleware),
    initialState = CityState.initial,
), CityCallbacks {

    init {
        handleAction(CityAction.Start)
    }

    override fun onCityClick(city: CityResultModel) {
        Timber.tag(TAG).d("Clicked on city [$city]")
        navigator.cityToWeather(city.toSelectedCity())
    }

    override fun onQueryChange(query: String) {
        handleAction(CityAction.PrefixUpdated(query))
    }

    private fun CityResultModel.toSelectedCity() = SelectedCity(
        name = name.toString(),
        country = country.toString(),
        countryCode = countryCode,
    )
}
