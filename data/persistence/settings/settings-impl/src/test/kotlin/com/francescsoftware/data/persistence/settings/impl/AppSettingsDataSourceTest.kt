package com.francescsoftware.data.persistence.settings.impl

import androidx.datastore.core.DataStore
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.francescsoftware.weathersample.data.persistence.settings.api.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import javax.inject.Named

internal class AppSettingsDataSourceTest {

    private class TestDataStore : DataStore<AppPreferences> {
        private val defaultInstance = AppPreferences.getDefaultInstance().toBuilder()
            .setAppTheme(AppPreferences.Theme.SYSTEM)
            .setDynamicColors(true)
            .build()

        private val preferences = MutableStateFlow(defaultInstance)

        override val data: Flow<AppPreferences>
            get() = preferences.asStateFlow()

        override suspend fun updateData(transform: suspend (t: AppPreferences) -> AppPreferences): AppPreferences {
            preferences.value = transform(preferences.value)
            return preferences.value
        }

        fun reset() {
            preferences.value = defaultInstance
        }
    }

    private val testDataStore = TestDataStore()

    private val appSettingsDataSource = AppSettingsDataSourceImpl(
        appSettingsStore = testDataStore,
    )

    @AfterEach
    fun cleanUp() {
        testDataStore.reset()
    }

    @Test
    @Named("Data source returns expected defaults")
    fun dataSourceReturnsDefaults() = runTest {
        appSettingsDataSource.settings.test {
            val item = awaitItem()
            assertThat(item.appTheme).isEqualTo(AppTheme.System)
            assertThat(item.dynamicColor).isTrue()
        }
    }

    @Test
    @Named("Data source persists app theme")
    fun dataSourcePersistsTheme() = runTest {
        appSettingsDataSource.settings.test {
            skipItems(1)
            appSettingsDataSource.setTheme(AppTheme.Dark)
            var item = awaitItem()
            assertThat(item.appTheme).isEqualTo(AppTheme.Dark)
            appSettingsDataSource.setTheme(AppTheme.Light)
            item = awaitItem()
            assertThat(item.appTheme).isEqualTo(AppTheme.Light)
            appSettingsDataSource.setTheme(AppTheme.System)
            item = awaitItem()
            assertThat(item.appTheme).isEqualTo(AppTheme.System)
        }
    }

    @Test
    @Named("Data source persists dynamic colors")
    fun dataSourcePersistsDynamicColors() = runTest {
        appSettingsDataSource.settings.test {
            skipItems(1)
            appSettingsDataSource.setUseDynamicColor(false)
            var item = awaitItem()
            assertThat(item.dynamicColor).isFalse()
            appSettingsDataSource.setUseDynamicColor(true)
            item = awaitItem()
            assertThat(item.dynamicColor).isTrue()
        }
    }
}
