package com.francescsoftware.weathersample.feature.city.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.feature.city.model.RecentCityModel
import com.francescsoftware.weathersample.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.interactor.city.api.InsertRecentCityInteractor
import com.francescsoftware.weathersample.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.shared.mvi.Middleware
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
            is CityAction.OnCityClick -> saveCity(action.cityModel)
            is CityAction.OnChipClick -> onChipClick(action.recentCityModel)
            is CityAction.OnDeleteChipClick -> onDeleteChip(action.recentCityModel)
            else -> {}
        }
    }

    private fun loadRecentCities() {
        if (recentsJob == null) {
            recentsJob = getRecentCitiesInteractor.execute(limit = MaxRecentCities)
                .map { cities -> cities.map { city -> RecentCityModel(name = city.name) } }
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

    private fun saveCity(recentCityModel: RecentCityModel) {
        scope.launch {
            insertRecentCitiesInteractor.execute(RecentCity(name = recentCityModel.name))
        }
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

    private fun onDeleteChip(recentCityModel: RecentCityModel) {
        scope.launch {
            deleteRecentCityInteractor.execute(city = RecentCity(name = recentCityModel.name))
        }
    }
}
