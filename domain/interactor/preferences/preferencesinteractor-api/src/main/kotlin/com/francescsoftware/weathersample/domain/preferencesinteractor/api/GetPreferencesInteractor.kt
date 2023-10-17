package com.francescsoftware.weathersample.domain.preferencesinteractor.api

import com.francescsoftware.weathersample.domain.interactor.foundation.StreamInteractor

data class AppSettings(
    val appTheme: AppTheme,
    val dynamicColor: Boolean,
)

abstract class GetPreferencesInteractor : StreamInteractor<Unit, AppSettings>()
