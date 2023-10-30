package com.francescsoftware.weathersample.data.repository.favorites.api

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
     * @param cityId the ID of the city to delete
     */
    suspend fun deleteFavoriteCity(cityId: Long)
}
