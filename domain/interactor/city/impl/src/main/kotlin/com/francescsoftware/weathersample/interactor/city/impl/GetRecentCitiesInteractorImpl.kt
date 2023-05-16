package com.francescsoftware.weathersample.interactor.city.impl

import com.francescsoftware.weathersample.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.repository.recents.api.RecentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetRecentCitiesInteractorImpl @Inject constructor(
    private val recentsRepository: RecentsRepository
) : GetRecentCitiesInteractor {

    override operator fun invoke(limit: Int): Flow<List<RecentCity>> = recentsRepository
        .getRecentCities(limit = limit)
        .map { cities ->
            cities.map { city ->
                RecentCity(name = city.name)
            }
        }
}
