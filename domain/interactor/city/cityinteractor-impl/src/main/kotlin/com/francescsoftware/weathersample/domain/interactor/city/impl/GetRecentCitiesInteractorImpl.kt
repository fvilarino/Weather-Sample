package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.data.repository.recents.api.RecentsRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class GetRecentCitiesInteractorImpl @Inject constructor(
    private val recentsRepository: RecentsRepository,
) : GetRecentCitiesInteractor() {

    override fun buildStream(params: Params) = recentsRepository
        .getRecentCities(limit = params.limit)
        .map { cities ->
            cities.map { city ->
                RecentCity(name = city.name)
            }
        }
}
