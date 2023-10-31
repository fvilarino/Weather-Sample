package com.francescsoftware.weathersample.data.persistence.settings.api

import kotlinx.coroutines.flow.MutableStateFlow

class FakeAppSettingsDataSource : AppSettingsDataSource {

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
