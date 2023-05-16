package com.francescsoftware.weathersample.data.repository.recents.api

import kotlinx.coroutines.flow.Flow

private const val MaxRecentCities = 10

/** Repository for recent cities */
interface RecentsRepository {
    /**
     * Gets a list of recent cities
     *
     * @param limit - maximum number of results to return
     * @return a [Flow] of a [List] of [RecentCity]
     */
    fun getRecentCities(limit: Int = MaxRecentCities): Flow<List<RecentCity>>

    /**
     * Stores a [RecentCity]
     *
     * @param recentCity - the [RecentCity] to persist
     */
    suspend fun saveRecentCity(recentCity: RecentCity)

    /**
     * Deletes a [RecentCity]
     *
     * @param recentCity - the [RecentCity] to delete
     */
    suspend fun deleteRecentCity(recentCity: RecentCity)
}
