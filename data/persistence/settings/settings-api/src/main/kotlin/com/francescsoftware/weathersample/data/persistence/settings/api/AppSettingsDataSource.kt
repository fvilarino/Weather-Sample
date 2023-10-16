package com.francescsoftware.weathersample.data.persistence.settings.api

import kotlinx.coroutines.flow.Flow

interface AppSettingsDataSource {
    val settings: Flow<AppSettings>

    suspend fun setTheme(appTheme: AppTheme)
    suspend fun setUseDynamicColor(useDynamicColor: Boolean)
}
