package com.francescsoftware.weathersample.domain.preferences.preferencesinteractor.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.francescsoftware.weathersample.data.persistence.settings.api.FakeAppSettingsDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import javax.inject.Named

internal class SetDynamicColorInteractorTest {

    private val fakeDataSource = FakeAppSettingsDataSource()
    private val setUseDynamicColorsInteractor = SetUseDynamicColorsInteractorImpl(fakeDataSource)

    @Named("Interactor sets use dynamic color")
    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun interactorSetsAppTheme(
        useDynamic: Boolean,
    ) = runTest {
        setUseDynamicColorsInteractor.invoke(useDynamic)
        val dynamic = fakeDataSource.settings.first().dynamicColor
        assertThat(dynamic).isEqualTo(useDynamic)
    }
}
