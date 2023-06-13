package com.francescsoftware.weathersample.data.repository.favorite.impl

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCitiesDatabase
import com.francescsoftware.weathersample.data.repository.favorites.api.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface FavoriteRepositoryModule {
    @Binds
    @Singleton
    fun bindFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl,
    ): FavoriteRepository
}

@Module
@InstallIn(SingletonComponent::class)
internal object FavoriteDatabaseModule {
    @Provides
    @Singleton
    fun provideCitiesDatabase(
        @ApplicationContext context: Context,
    ): FavoriteCitiesDatabase =
        databaseBuilder(
            context,
            FavoriteCitiesDatabase::class.java,
            "favorite_cities.db",
        ).build()
}
