package com.francescsoftware.weathersample.ui.feature.search.city.presenter

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val MaxRecentCities = 10

class RecentCitiesLoader @Inject constructor(
    getRecentCitiesInteractor: GetRecentCitiesInteractor,
    private val dispatcherProvider: DispatcherProvider,
) {
    init {
        getRecentCitiesInteractor(GetRecentCitiesInteractor.Params(limit = MaxRecentCities))
    }

    val recentCities: Flow<ImmutableList<RecentCityModel>> = getRecentCitiesInteractor.stream
        .map { recentCities -> parseRecentCities(recentCities) }
        .distinctUntilChanged()

    private suspend fun parseRecentCities(cities: List<RecentCity>) =
        withContext(dispatcherProvider.default) {
            cities.map { city ->
                RecentCityModel(name = city.name)
            }
                .sortedBy { city -> city.name }
                .toPersistentList()
        }
}
