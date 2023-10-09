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

private const val MaxRecentCities = 10

internal class RecentCitiesLoader(
    getRecentCitiesInteractor: GetRecentCitiesInteractor,
    private val dispatcherProvider: DispatcherProvider,
) {
    val recentCities: Flow<ImmutableList<RecentCityModel>> = getRecentCitiesInteractor(limit = MaxRecentCities)
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
