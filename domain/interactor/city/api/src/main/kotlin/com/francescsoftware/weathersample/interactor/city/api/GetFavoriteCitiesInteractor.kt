package com.francescsoftware.weathersample.interactor.city.api

import com.francescsoftware.weathersample.interactor.city.api.model.FavoriteCity
import kotlinx.coroutines.flow.Flow

/** Loads the favorite cities */
interface GetFavoriteCitiesInteractor {
    /**
     * Loads the favorite cities
     *
     * @return a [Flow] of [List] of [FavoriteCity]
     */
    operator fun invoke(): Flow<List<FavoriteCity>>
}
