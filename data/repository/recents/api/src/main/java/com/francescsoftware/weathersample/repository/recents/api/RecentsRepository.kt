package com.francescsoftware.weathersample.repository.recents.api

import kotlinx.coroutines.flow.Flow

private const val MaxRecentCities = 10

interface RecentsRepository {
    fun getRecentCities(limit: Int = MaxRecentCities): Flow<List<RecentCity>>
    suspend fun saveRecentCity(recentCity: RecentCity)
    suspend fun deleteRecentCity(recentCity: RecentCity)
}
