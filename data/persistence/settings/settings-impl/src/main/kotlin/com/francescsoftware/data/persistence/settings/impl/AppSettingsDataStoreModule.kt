package com.francescsoftware.data.persistence.settings.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.francescsoftware.weathersample.core.coroutines.ApplicationScope
import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.ApplicationContext
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope

private const val AppSettingsFile = "app_settings.pref"

@Module
@ContributesTo(AppScope::class)
object AppSettingsDataStoreModule {
    @Provides
    @SingleIn(AppScope::class)
    fun provideAppSettingsDataStore(
        @ApplicationContext context: Context,
        @ApplicationScope scope: CoroutineScope,
        dispatcherProvider: DispatcherProvider,
    ): DataStore<AppPreferences> =
        DataStoreFactory.create(
            serializer = AppSettingsSerializer,
            scope = CoroutineScope(scope.coroutineContext + dispatcherProvider.io),
        ) {
            context.dataStoreFile(AppSettingsFile)
        }
}
