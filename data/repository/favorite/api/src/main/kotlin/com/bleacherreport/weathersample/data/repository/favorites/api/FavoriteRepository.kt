package com.bleacherreport.weathersample.data.repository.favorites.api

import kotlinx.coroutines.flow.Flow

/** Repository for favorite cities */
interface FavoriteRepository {
    /**
     * Gets the favorite cities
     *
     * @return a [Flow] of a [List] of [FavoriteCity]
     */
    fun getFavoriteCities(): Flow<List<FavoriteCity>>

    /**
     * Stores a [FavoriteCity]
     *
     * @param favoriteCity the [FavoriteCity] to persist
     */
    suspend fun saveFavoriteCity(favoriteCity: FavoriteCity)

    /**
     * Deletes a [FavoriteCity] from storage
     *
     * @param favoriteCity the [FavoriteCity] to delete
     */
    suspend fun deleteFavoriteCity(favoriteCity: FavoriteCity)
}
