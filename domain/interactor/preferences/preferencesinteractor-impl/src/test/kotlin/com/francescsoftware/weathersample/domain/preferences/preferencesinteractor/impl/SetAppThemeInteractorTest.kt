package com.francescsoftware.weathersample.domain.preferences.preferencesinteractor.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.francescsoftware.weathersample.data.persistence.settings.api.FakeAppSettingsDataSource
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import com.francescsoftware.weathersample.data.persistence.settings.api.AppTheme as DataAppTheme

internal class SetAppThemeInteractorTest {

    private val fakeDataSource = FakeAppSettingsDataSource()
    private val setAppThemeInteractor = SetAppThemeInteractorImpl(fakeDataSource)

    @DisplayName("Interactor sets app theme")
    @ParameterizedTest
    @EnumSource(AppTheme::class)
    fun interactorSetsAppTheme(
        appTheme: AppTheme,
    ) = runTest {
        setAppThemeInteractor.invoke(appTheme)
        val theme = fakeDataSource.settings.first().appTheme
        val actual = when (theme) {
            DataAppTheme.System -> AppTheme.System
            DataAppTheme.Light -> AppTheme.Light
            DataAppTheme.Dark -> AppTheme.Dark
        }
        assertThat(actual).isEqualTo(appTheme)
    }
}
