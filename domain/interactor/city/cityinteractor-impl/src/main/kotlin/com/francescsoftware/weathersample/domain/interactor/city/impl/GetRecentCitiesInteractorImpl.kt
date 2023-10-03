package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.data.repository.recents.api.RecentsRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class GetRecentCitiesInteractorImpl @Inject constructor(
    private val recentsRepository: RecentsRepository,
) : GetRecentCitiesInteractor {

    override operator fun invoke(limit: Int): Flow<List<RecentCity>> = recentsRepository
        .getRecentCities(limit = limit)
        .map { cities ->
            cities.map { city ->
                RecentCity(name = city.name)
            }
        }
}
