package com.francescsoftware.weathersample.interactor.city.impl

import com.bleacherreport.weathersample.repository.favorites.api.FavoriteRepository
import com.francescsoftware.weathersample.interactor.city.api.InsertFavoriteCityInteractor
import com.francescsoftware.weathersample.interactor.city.api.model.FavoriteCity
import javax.inject.Inject

internal class InsertFavoriteCityInteractorImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : InsertFavoriteCityInteractor {

    override suspend fun invoke(city: FavoriteCity) = favoriteRepository
        .saveFavoriteCity(city.toRepoFavoriteCity())
}
