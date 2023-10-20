package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.data.repository.favorites.api.FavoriteRepository
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCityIdsInteractor
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class GetFavoriteCityIdsInteractorImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : GetFavoriteCityIdsInteractor() {

    override fun buildStream(params: Unit): Flow<Set<Long>> = favoriteRepository
        .getFavoriteCities()
        .map { cities ->
            cities.map { city ->
                city.cityId
            }.toSet()
        }
}
