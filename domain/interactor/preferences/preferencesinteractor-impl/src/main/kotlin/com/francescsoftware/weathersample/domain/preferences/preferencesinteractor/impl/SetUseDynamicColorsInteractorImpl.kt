package com.francescsoftware.weathersample.domain.preferences.preferencesinteractor.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.data.persistence.settings.api.AppSettingsDataSource
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.SetUseDynamicColorsInteractor
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class SetUseDynamicColorsInteractorImpl @Inject constructor(
    private val dataSource: AppSettingsDataSource,
) : SetUseDynamicColorsInteractor {

    override suspend fun invoke(params: Boolean) {
        dataSource.setUseDynamicColor(params)
    }
}
