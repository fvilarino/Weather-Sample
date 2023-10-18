package com.francescsoftware.weathersample.domain.preferences.preferencesinteractor.impl

import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppTheme
import com.francescsoftware.weathersample.data.persistence.settings.api.AppTheme as DataAppTheme

internal val DataAppTheme.toDomainTheme
    get() = when (this) {
        DataAppTheme.System -> AppTheme.System
        DataAppTheme.Light -> AppTheme.Light
        DataAppTheme.Dark -> AppTheme.Dark
    }
