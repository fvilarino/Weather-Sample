package com.francescsoftware.weathersample.data.repository.recents.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentCitiesDao {
    @Query("SELECT * FROM RecentCityEntity ORDER BY last_used DESC LIMIT :limit")
    fun getCities(limit: Int): Flow<List<RecentCityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentCity(recentCityEntity: RecentCityEntity)

    @Query("DELETE FROM RecentCityEntity where name = :name")
    suspend fun deleteRecentCity(name: String)
}
