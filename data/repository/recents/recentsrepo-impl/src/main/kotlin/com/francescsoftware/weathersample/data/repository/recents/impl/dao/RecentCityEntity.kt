package com.francescsoftware.weathersample.data.repository.recents.impl.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentCityEntity(
    @PrimaryKey val name: String,
    @ColumnInfo("last_used") val lastUsed: Long,
)
