package com.francescsoftware.weathersample.domain.preferences.preferencesinteractor.impl

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.francescsoftware.weathersample.data.persistence.settings.api.AppTheme
import com.francescsoftware.weathersample.data.persistence.settings.api.FakeAppSettingsDataSource
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class GetPreferencesInteractorTest {

    private val fakeDataSource = FakeAppSettingsDataSource()
    private val getPreferencesInteractor = GetPreferencesInteractorImpl(fakeDataSource)

    @DisplayName("Interactor maps data source objects to domain objects")
    @ParameterizedTest
    @EnumSource(AppTheme::class)
    fun interactorMapping(
        theme: AppTheme,
    ) = runTest {
        getPreferencesInteractor.invoke(Unit)
        fakeDataSource.setTheme(theme)
        fakeDataSource.setUseDynamicColor(true)
        getPreferencesInteractor.stream.test {
            val item = awaitItem()
            assertThat(item.appTheme).isEqualTo(theme.toDomainTheme)
            assertThat(item.dynamicColor).isTrue()
        }
    }
}
