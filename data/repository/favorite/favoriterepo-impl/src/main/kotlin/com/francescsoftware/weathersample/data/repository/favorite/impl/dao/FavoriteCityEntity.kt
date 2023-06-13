package com.francescsoftware.weathersample.data.repository.favorite.impl.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class FavoriteCityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo("country_code")
    val countryCode: String,
)
