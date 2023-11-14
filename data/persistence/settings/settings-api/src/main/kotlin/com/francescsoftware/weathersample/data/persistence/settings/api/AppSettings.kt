package com.francescsoftware.weathersample.data.persistence.settings.api

/**
 * App settings
 *
 * @property appTheme selected app theme, one of [AppTheme]
 * @property dynamicColor whether dynamic color is enabled
 */
data class AppSettings(
    val appTheme: AppTheme,
    val dynamicColor: Boolean,
)
