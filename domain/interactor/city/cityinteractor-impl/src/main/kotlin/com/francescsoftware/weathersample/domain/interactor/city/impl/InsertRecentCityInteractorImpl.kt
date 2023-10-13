package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.data.repository.recents.api.RecentsRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertRecentCityInteractor
import com.squareup.anvil.annotations.ContributesBinding
import java.util.Locale
import javax.inject.Inject
import com.francescsoftware.weathersample.data.repository.recents.api.RecentCity as RepoCity

@ContributesBinding(AppScope::class)
class InsertRecentCityInteractorImpl @Inject constructor(
    private val recentsRepository: RecentsRepository,
) : InsertRecentCityInteractor {

    override suspend fun invoke(params: InsertRecentCityInteractor.Params) {
        recentsRepository.saveRecentCity(
            RepoCity(
                name = params.city.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) },
            ),
        )
    }
}
