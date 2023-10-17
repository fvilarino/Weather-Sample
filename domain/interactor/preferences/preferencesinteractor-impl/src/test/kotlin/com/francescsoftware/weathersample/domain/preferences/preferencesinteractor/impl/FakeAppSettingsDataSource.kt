package com.francescsoftware.weathersample.domain.preferences.preferencesinteractor.impl

import com.francescsoftware.weathersample.data.persistence.settings.api.AppSettings
import com.francescsoftware.weathersample.data.persistence.settings.api.AppSettingsDataSource
import com.francescsoftware.weathersample.data.persistence.settings.api.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeAppSettingsDataSource : AppSettingsDataSource {

    override val settings = MutableStateFlow(
        AppSettings(
            AppTheme.System,
            true,
        ),
    )

    override suspend fun setTheme(appTheme: AppTheme) {
        settings.value = settings.value.copy(
            appTheme = appTheme,
        )
    }

    override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        settings.value = settings.value.copy(
            dynamicColor = useDynamicColor,
        )
    }
}
