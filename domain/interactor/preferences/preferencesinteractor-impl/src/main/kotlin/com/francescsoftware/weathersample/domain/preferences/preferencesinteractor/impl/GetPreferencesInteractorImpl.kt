package com.francescsoftware.weathersample.domain.preferences.preferencesinteractor.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.data.persistence.settings.api.AppSettingsDataSource
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppSettings
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppTheme
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.GetPreferencesInteractor
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.francescsoftware.weathersample.data.persistence.settings.api.AppTheme as DataAppTheme

@ContributesBinding(AppScope::class)
class GetPreferencesInteractorImpl @Inject constructor(
    private val dataSource: AppSettingsDataSource,
) : GetPreferencesInteractor() {

    override fun buildStream(params: Unit): Flow<AppSettings> = dataSource.settings
        .map { settings ->
            AppSettings(
                appTheme = when (settings.appTheme) {
                    DataAppTheme.System -> AppTheme.System
                    DataAppTheme.Light -> AppTheme.Light
                    DataAppTheme.Dark -> AppTheme.Light
                },
                dynamicColor = settings.dynamicColor,
            )
        }
        .distinctUntilChanged()
}
