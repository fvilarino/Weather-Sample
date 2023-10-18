package com.francescsoftware.weathersample.domain.preferences.preferencesinteractor.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.data.persistence.settings.api.AppSettingsDataSource
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppTheme
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.SetAppThemeInteractor
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import com.francescsoftware.weathersample.data.persistence.settings.api.AppTheme as DataAppTheme

@ContributesBinding(AppScope::class)
class SetAppThemeInteractorImpl @Inject constructor(
    private val dataSource: AppSettingsDataSource,
) : SetAppThemeInteractor {

    override suspend fun invoke(params: AppTheme) {
        dataSource.setTheme(
            when (params) {
                AppTheme.System -> DataAppTheme.System
                AppTheme.Light -> DataAppTheme.Light
                AppTheme.Dark -> DataAppTheme.Dark
            },
        )
    }
}
