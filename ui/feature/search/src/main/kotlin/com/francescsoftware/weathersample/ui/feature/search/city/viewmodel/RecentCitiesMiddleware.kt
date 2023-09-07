package com.francescsoftware.weathersample.ui.feature.search.city.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.core.coroutines.CloseableCoroutineScope
import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.navigation.SelectedCity
import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val MaxRecentCities = 10

internal class RecentCitiesMiddleware @Inject constructor(
    private val getRecentCitiesInteractor: GetRecentCitiesInteractor,
    private val insertRecentCitiesInteractor: InsertRecentCityInteractor,
    private val deleteRecentCityInteractor: DeleteRecentCityInteractor,
    private val dispatcherProvider: DispatcherProvider,
    private val scope: CloseableCoroutineScope,
) : Middleware<CityState, CityAction>(
    closeables = arrayOf(scope),
) {

    private var recentsJob: Job? = null

    override suspend fun process(
        state: CityState,
        action: CityAction,
    ) {
        when (action) {
            CityAction.QueryFocused -> loadRecentCities()
            is CityAction.OnCityClick -> saveCity(action.selectedCity)
            is CityAction.OnChipClick -> onChipClick(action.recentCityModel)
            is CityAction.OnDeleteChipClick -> onDeleteChip(action.recentCityModel)
            else -> {}
        }
    }

    private fun loadRecentCities() {
        if (recentsJob == null) {
            recentsJob = scope.launch {
                getRecentCitiesInteractor(limit = MaxRecentCities)
                    .map { cities ->
                        parseCities(cities)
                    }
                    .collect { recentCities ->
                        if (recentCities.isEmpty()) {
                            dispatch(
                                CityAction.HideRecentCities,
                            )
                        } else {
                            dispatch(
                                CityAction.RecentCitiesLoaded(recentCities = recentCities),
                            )
                        }
                    }
            }
        }
    }

    private suspend fun parseCities(cities: List<RecentCity>) = withContext(dispatcherProvider.default) {
        cities.map { city ->
            RecentCityModel(name = city.name)
        }.toPersistentList()
    }

    private suspend fun saveCity(selectedCity: SelectedCity) {
        insertRecentCitiesInteractor(RecentCity(name = selectedCity.name))
    }

    private suspend fun onDeleteChip(recentCityModel: RecentCityModel) {
        deleteRecentCityInteractor(city = RecentCity(name = recentCityModel.name))
    }

    private fun onChipClick(recentCityModel: RecentCityModel) {
        dispatch(
            CityAction.QueryUpdated(
                TextFieldValue(
                    text = recentCityModel.name,
                    selection = TextRange(
                        recentCityModel.name.length,
                    ),
                ),
            ),
        )
    }
}
