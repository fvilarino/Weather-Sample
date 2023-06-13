package com.francescsoftware.weathersample.data.repository.favorite.impl.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCityEntity::class], version = 1, exportSchema = true)
internal abstract class FavoriteCitiesDatabase : RoomDatabase() {
    abstract fun favoriteCitiesDao(): FavoriteCitiesDao
}
