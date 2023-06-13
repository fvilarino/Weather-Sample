package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.data.repository.favorites.api.FavoriteRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetFavoriteCitiesInteractorImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : GetFavoriteCitiesInteractor {

    override fun invoke(): Flow<List<FavoriteCity>> = favoriteRepository
        .getFavoriteCities()
        .map { cities ->
            cities.map { city ->
                city.toFavoriteCity()
            }
        }
}
