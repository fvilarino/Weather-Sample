package com.francescsoftware.weathersample.repository.recents.impl

import com.francescsoftware.weathersample.repository.recents.api.RecentCity
import com.francescsoftware.weathersample.repository.recents.api.RecentsRepository
import com.francescsoftware.weathersample.repository.recents.impl.dao.RecentCitiesDao
import com.francescsoftware.weathersample.repository.recents.impl.dao.RecentCitiesDatabase
import com.francescsoftware.weathersample.repository.recents.impl.dao.RecentCityEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RecentsRepositoryImpl @Inject constructor(
    private val recentCitiesDatabase: RecentCitiesDatabase,
) : RecentsRepository {

    private val dao: RecentCitiesDao
        get() = recentCitiesDatabase.recentCitiesDao()

    override fun getRecentCities(limit: Int): Flow<List<RecentCity>> =
        dao.getCities(limit = limit)
            .map { recents ->
                recents.map { recent ->
                    RecentCity(name = recent.name)
                }
            }

    override suspend fun saveRecentCity(recentCity: RecentCity) {
        dao.insertRecentCity(
            RecentCityEntity(
                name = recentCity.name,
                lastUsed = System.currentTimeMillis(),
            )
        )
    }

    override suspend fun deleteRecentCity(recentCity: RecentCity) {
        dao.deleteRecentCity(name = recentCity.name)
    }
}
