package com.francescsoftware.weathersample.data.repository.recents.impl

import android.content.Context
import androidx.room.Room
import com.francescsoftware.weathersample.data.repository.recents.api.RecentsRepository
import com.francescsoftware.weathersample.data.repository.recents.impl.dao.RecentCitiesDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RecentsRepositoryModule {
    @Binds
    @Singleton
    fun bindRecentsRepository(
        recentsRepositoryImpl: RecentsRepositoryImpl,
    ): RecentsRepository
}

@Module
@InstallIn(SingletonComponent::class)
internal object CitiesDatabaseModule {
    @Provides
    @Singleton
    fun provideCitiesDatabase(
        @ApplicationContext context: Context,
    ): RecentCitiesDatabase =
        Room.databaseBuilder(
            context,
            RecentCitiesDatabase::class.java,
            "recent_cities.db",
        ).build()
}
