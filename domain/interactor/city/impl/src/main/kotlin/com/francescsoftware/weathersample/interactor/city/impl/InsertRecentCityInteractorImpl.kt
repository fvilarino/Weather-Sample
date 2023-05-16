package com.francescsoftware.weathersample.interactor.city.impl

import com.francescsoftware.weathersample.interactor.city.api.InsertRecentCityInteractor
import com.francescsoftware.weathersample.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.repository.recents.api.RecentsRepository
import java.util.Locale
import javax.inject.Inject
import com.francescsoftware.weathersample.repository.recents.api.RecentCity as RepoCity

internal class InsertRecentCityInteractorImpl @Inject constructor(
    private val recentsRepository: RecentsRepository
) : InsertRecentCityInteractor {

    override suspend operator fun invoke(city: RecentCity) {
        recentsRepository.saveRecentCity(
            RepoCity(
                name = city.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }
            )
        )
    }
}
