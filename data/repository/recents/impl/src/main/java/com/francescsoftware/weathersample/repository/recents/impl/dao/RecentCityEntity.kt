package com.francescsoftware.weathersample.repository.recents.impl.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class RecentCityEntity(
    @PrimaryKey val name: String,
    @ColumnInfo("last_used") val lastUsed: Long,
)
