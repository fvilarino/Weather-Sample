package com.francescsoftware.weathersample.interactor.city.impl

import com.francescsoftware.weathersample.data.repository.favorites.api.FavoriteRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.domain.interactor.city.impl.toRepoFavoriteCity
import javax.inject.Inject

internal class DeleteFavoriteCityInteractorImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : DeleteFavoriteCityInteractor {

    override suspend fun invoke(city: FavoriteCity) =
        favoriteRepository.deleteFavoriteCity(city.toRepoFavoriteCity())
}
