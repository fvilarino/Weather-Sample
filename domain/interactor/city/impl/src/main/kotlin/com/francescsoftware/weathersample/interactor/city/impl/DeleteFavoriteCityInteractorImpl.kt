package com.francescsoftware.weathersample.interactor.city.impl

import com.bleacherreport.weathersample.repository.favorites.api.FavoriteRepository
import com.francescsoftware.weathersample.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.interactor.city.api.model.FavoriteCity
import javax.inject.Inject

internal class DeleteFavoriteCityInteractorImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : DeleteFavoriteCityInteractor {

    override suspend fun invoke(city: FavoriteCity) =
        favoriteRepository.deleteFavoriteCity(city.toRepoFavoriteCity())
}
