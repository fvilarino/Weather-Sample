package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.data.repository.recents.api.RecentsRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import com.francescsoftware.weathersample.data.repository.recents.api.RecentCity as RepoCity

@ContributesBinding(AppScope::class)
class DeleteRecentCityInteractorImpl @Inject constructor(
    private val recentsRepository: RecentsRepository,
) : DeleteRecentCityInteractor {

    override suspend operator fun invoke(city: RecentCity) {
        recentsRepository.deleteRecentCity(
            RepoCity(name = city.name),
        )
    }
}
