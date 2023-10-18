package com.francescsoftware.data.persistence.settings.impl

import androidx.datastore.core.DataStore
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.data.persistence.settings.api.AppSettings
import com.francescsoftware.weathersample.data.persistence.settings.api.AppSettingsDataSource
import com.francescsoftware.weathersample.data.persistence.settings.api.AppTheme
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class AppSettingsDataSourceImpl @Inject constructor(
    private val appSettingsStore: DataStore<AppPreferences>,
) : AppSettingsDataSource {

    override val settings: Flow<AppSettings>
        get() = appSettingsStore.data
            .map { settings ->
                AppSettings(
                    appTheme = settings.appTheme.toAppTheme,
                    dynamicColor = settings.dynamicColors,
                )
            }
            .distinctUntilChanged()

    override suspend fun setTheme(appTheme: AppTheme) {
        appSettingsStore.updateData { appPreferences ->
            appPreferences.copy {
                this.appTheme = appTheme.toPreferencesTheme
            }
        }
    }

    override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        appSettingsStore.updateData { appPreferences ->
            appPreferences.copy {
                this.dynamicColors = useDynamicColor
            }
        }
    }

    private val AppPreferences.Theme?.toAppTheme: AppTheme
        get() = when (this) {
            AppPreferences.Theme.SYSTEM -> AppTheme.System
            AppPreferences.Theme.LIGHT -> AppTheme.Light
            AppPreferences.Theme.DARK -> AppTheme.Dark
            AppPreferences.Theme.UNRECOGNIZED -> AppTheme.System
            null -> AppTheme.System
        }

    private val AppTheme.toPreferencesTheme: AppPreferences.Theme
        get() = when (this) {
            AppTheme.System -> AppPreferences.Theme.SYSTEM
            AppTheme.Light -> AppPreferences.Theme.LIGHT
            AppTheme.Dark -> AppPreferences.Theme.DARK
        }
}
