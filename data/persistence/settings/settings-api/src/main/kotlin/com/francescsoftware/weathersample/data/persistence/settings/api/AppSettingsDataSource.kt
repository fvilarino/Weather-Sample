package com.francescsoftware.weathersample.data.persistence.settings.api

import kotlinx.coroutines.flow.Flow

/** Settings data source */
interface AppSettingsDataSource {
    val settings: Flow<AppSettings>

    /**
     * Updates the theme to use in the app
     *
     * @param appTheme [AppTheme] to apply to the app
     */
    suspend fun setTheme(appTheme: AppTheme)

    /**
     * Sets the usage of dynamic color
     *
     * @param useDynamicColor whether dynamic color is enabled or disabled
     */
    suspend fun setUseDynamicColor(useDynamicColor: Boolean)
}
