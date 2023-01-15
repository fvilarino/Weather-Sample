package com.francescsoftware.weathersample.interactor.city.impl

import com.francescsoftware.weathersample.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.repository.recents.api.RecentsRepository
import javax.inject.Inject
import com.francescsoftware.weathersample.repository.recents.api.RecentCity as RepoCity

internal class DeleteRecentCityInteractorImpl @Inject constructor(
    private val recentsRepository: RecentsRepository
) : DeleteRecentCityInteractor {
    override suspend fun execute(city: RecentCity) {
        recentsRepository.deleteRecentCity(
            RepoCity(name = city.name)
        )
    }
}
