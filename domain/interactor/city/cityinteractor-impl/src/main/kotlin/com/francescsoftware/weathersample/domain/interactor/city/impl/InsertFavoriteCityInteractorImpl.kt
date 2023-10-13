package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.data.repository.favorites.api.FavoriteRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertFavoriteCityInteractor
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class InsertFavoriteCityInteractorImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : InsertFavoriteCityInteractor {

    override suspend fun invoke(params: InsertFavoriteCityInteractor.Param) = favoriteRepository
        .saveFavoriteCity(params.city.toRepoFavoriteCity())
}
