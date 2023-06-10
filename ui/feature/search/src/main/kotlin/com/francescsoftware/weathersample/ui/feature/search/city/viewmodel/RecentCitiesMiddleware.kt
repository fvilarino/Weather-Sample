package com.francescsoftware.weathersample.ui.feature.search.city.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.navigation.SelectedCity
import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MaxRecentCities = 10

internal class RecentCitiesMiddleware @Inject constructor(
    private val getRecentCitiesInteractor: GetRecentCitiesInteractor,
    private val insertRecentCitiesInteractor: InsertRecentCityInteractor,
    private val deleteRecentCityInteractor: DeleteRecentCityInteractor,
) : Middleware<CityState, CityAction>() {

    private var recentsJob: Job? = null

    override fun process(
        state: CityState,
        action: CityAction,
    ) {
        when (action) {
            CityAction.QueryFocused -> loadRecentCities()
            is CityAction.OnCityClick -> scope.saveCity(action.selectedCity)
            is CityAction.OnChipClick -> onChipClick(action.recentCityModel)
            is CityAction.OnDeleteChipClick -> scope.onDeleteChip(action.recentCityModel)
            else -> {}
        }
    }

    private fun loadRecentCities() {
        if (recentsJob == null) {
            recentsJob = getRecentCitiesInteractor(limit = MaxRecentCities)
                .map { cities ->
                    cities.map { city -> RecentCityModel(name = city.name) }.toPersistentList()
                }
                .onEach { recentCities ->
                    if (recentCities.isEmpty()) {
                        dispatch(
                            CityAction.HideRecentCities
                        )
                    } else {
                        dispatch(
                            CityAction.RecentCitiesLoaded(recentCities = recentCities)
                        )
                    }
                }
                .launchIn(scope)
        }
    }

    private fun CoroutineScope.saveCity(selectedCity: SelectedCity) = launch {
        insertRecentCitiesInteractor(RecentCity(name = selectedCity.name))
    }

    private fun onChipClick(recentCityModel: RecentCityModel) {
        dispatch(
            CityAction.QueryUpdated(
                TextFieldValue(
                    text = recentCityModel.name,
                    selection = TextRange(
                        recentCityModel.name.length,
                    ),
                )
            )
        )
    }

    private fun CoroutineScope.onDeleteChip(recentCityModel: RecentCityModel) = launch {
        deleteRecentCityInteractor(city = RecentCity(name = recentCityModel.name))
    }
}
