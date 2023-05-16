package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity

/** Stores a [RecentCity] in persistent memory */
interface InsertFavoriteCityInteractor {

    /**
     * Stores a [FavoriteCity]
     *
     * @param city - the city to store
     */
    suspend operator fun invoke(city: FavoriteCity)
}
