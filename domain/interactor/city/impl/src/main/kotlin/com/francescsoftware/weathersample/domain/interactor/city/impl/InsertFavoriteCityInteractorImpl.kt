package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.bleacherreport.weathersample.data.repository.favorites.api.FavoriteRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import javax.inject.Inject

internal class InsertFavoriteCityInteractorImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : InsertFavoriteCityInteractor {

    override suspend fun invoke(city: FavoriteCity) = favoriteRepository
        .saveFavoriteCity(city.toRepoFavoriteCity())
}
