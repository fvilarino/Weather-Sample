package com.francescsoftware.weathersample.data.repository.favorite.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCitiesDao {
    @Query("SELECT * FROM FavoriteCityEntity ORDER BY name ASC")
    fun getFavoriteCities(): Flow<List<FavoriteCityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCity(favoriteCityEntity: FavoriteCityEntity)

    @Query("DELETE FROM FavoriteCityEntity WHERE city_id = :cityId")
    suspend fun deleteFavoriteCity(cityId: Long)
}
