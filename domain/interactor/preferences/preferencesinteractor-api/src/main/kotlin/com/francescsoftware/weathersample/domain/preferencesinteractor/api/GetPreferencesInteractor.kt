package com.francescsoftware.weathersample.domain.preferencesinteractor.api

import com.francescsoftware.weathersample.domain.interactor.foundation.StreamInteractor

/**
 * Current app settings
 *
 * @property appTheme [AppTheme] to use
 * @property dynamicColor whether dynamic color is in use
 */
data class AppSettings(
    val appTheme: AppTheme,
    val dynamicColor: Boolean,
)

/** Fetches the current [AppSettings] */
abstract class GetPreferencesInteractor : StreamInteractor<Unit, AppSettings>()
