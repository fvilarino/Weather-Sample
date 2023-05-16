package com.francescsoftware.weathersample.interactor.city.api

import com.francescsoftware.weathersample.interactor.city.api.model.FavoriteCity

/** Deletes an entry from recent cities */
interface DeleteFavoriteCityInteractor {
    /**
     * Deletes a [FavoriteCity]
     *
     * @param city - the [FavoriteCity] to delete
     */
    suspend operator fun invoke(city: FavoriteCity)
}
