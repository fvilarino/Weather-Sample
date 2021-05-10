package com.francescsoftware.weathersample.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.francescsoftware.weathersample.presentation.storage.SelectedCity
import com.francescsoftware.weathersample.storage.selectedcity.SelectedCitySerializer
import com.francescsoftware.weathersample.storage.selectedcity.SelectedCityStore
import com.francescsoftware.weathersample.storage.selectedcity.SelectedCityStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val SELECTED_CITY_STORAGE_FILE = "selected_city"

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Binds
    @Singleton
    abstract fun bindSelectedCityStore(
        selectedCityStore: SelectedCityStoreImpl
    ): SelectedCityStore
}

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideSelectedCityDataStore(
        @ApplicationContext context: Context,
    ): DataStore<SelectedCity> =
        DataStoreFactory.create(
            serializer = SelectedCitySerializer,
        ) {
            context.dataStoreFile(SELECTED_CITY_STORAGE_FILE)
        }
}
