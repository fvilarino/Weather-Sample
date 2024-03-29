package com.francescsoftware.weathersample.data.repository.recents.impl.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecentCityEntity::class], version = 1, exportSchema = true)
abstract class RecentCitiesDatabase : RoomDatabase() {
    abstract fun recentCitiesDao(): RecentCitiesDao
}
