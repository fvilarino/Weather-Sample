package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.bleacherreport.weathersample.data.repository.favorites.api.FavoriteRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import javax.inject.Inject

internal class DeleteFavoriteCityInteractorImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : DeleteFavoriteCityInteractor {

    override suspend fun invoke(city: FavoriteCity) =
        favoriteRepository.deleteFavoriteCity(city.toRepoFavoriteCity())
}
