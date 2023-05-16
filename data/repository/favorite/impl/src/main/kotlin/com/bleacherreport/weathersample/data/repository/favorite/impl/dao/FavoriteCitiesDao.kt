package com.bleacherreport.weathersample.data.repository.favorite.impl.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface FavoriteCitiesDao {
    @Query("SELECT * FROM FavoriteCityEntity ORDER BY name ASC")
    fun getFavoriteCities(): Flow<List<FavoriteCityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCity(favoriteCityEntity: FavoriteCityEntity)

    @Delete
    suspend fun deleteFavoriteCity(favoriteCityEntity: FavoriteCityEntity)
}
