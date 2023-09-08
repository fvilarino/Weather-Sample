package com.francescsoftware.weathersample.ui.feature.favorites.viewmodel

import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.ui.feature.favorites.ui.City
import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
internal class FavoriteCityMiddleware @Inject constructor(
    private val deleteFavoriteCityInteractor: DeleteFavoriteCityInteractor,
) : Middleware<FavoriteState, FavoriteAction>() {
    override suspend fun process(state: FavoriteState, action: FavoriteAction) {
        when (action) {
            is FavoriteAction.DeleteFavorite -> deleteFavorite(action.city)
            else -> {}
        }
    }

    private suspend fun deleteFavorite(city: City) {
        deleteFavoriteCityInteractor(
            FavoriteCity(
                id = city.favoriteId,
                name = city.name,
                countryCode = city.countryCode,
            ),
        )
    }
}
