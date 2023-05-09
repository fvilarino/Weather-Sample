package com.francescsoftware.weathersample.interactor.city.impl

import com.bleacherreport.weathersample.repository.favorites.api.FavoriteRepository
import com.francescsoftware.weathersample.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.interactor.city.api.model.FavoriteCity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetFavoriteCitiesInteractorImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : GetFavoriteCitiesInteractor {

    override fun invoke(limit: Int): Flow<List<FavoriteCity>> = favoriteRepository
        .getFavoriteCities()
        .map { cities ->
            cities.map { city ->
                city.toFavoriteCity()
            }
        }
}
