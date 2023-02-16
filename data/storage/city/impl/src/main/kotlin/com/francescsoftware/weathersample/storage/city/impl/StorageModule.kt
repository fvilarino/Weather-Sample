package com.francescsoftware.weathersample.storage.city.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.francescsoftware.weathersample.storage.city.api.SelectedCityStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val SelectedCityStorageFile = "selected_city"

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Binds
    @Singleton
    internal abstract fun bindSelectedCityStore(
        selectedCityStore: SelectedCityStoreImpl
    ): SelectedCityStore
}

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    internal fun provideSelectedCityDataStore(
        @ApplicationContext context: Context,
    ): DataStore<SelectedCity> =
        DataStoreFactory.create(
            serializer = SelectedCitySerializer,
        ) {
            context.dataStoreFile(SelectedCityStorageFile)
        }
}
