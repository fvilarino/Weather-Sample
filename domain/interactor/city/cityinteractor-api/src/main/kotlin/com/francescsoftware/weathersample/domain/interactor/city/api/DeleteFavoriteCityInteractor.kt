package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity

/** Deletes an entry from recent cities */
interface DeleteFavoriteCityInteractor {
    /**
     * Deletes a [FavoriteCity]
     *
     * @param city - the [FavoriteCity] to delete
     */
    suspend operator fun invoke(city: FavoriteCity)
}
