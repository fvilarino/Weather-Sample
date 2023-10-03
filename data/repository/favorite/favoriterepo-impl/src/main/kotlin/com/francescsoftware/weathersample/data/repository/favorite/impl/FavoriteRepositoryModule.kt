package com.francescsoftware.weathersample.data.repository.favorite.impl

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.ApplicationContext
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCitiesDatabase
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(AppScope::class)
internal object FavoriteDatabaseModule {
    @Provides
    @SingleIn(AppScope::class)
    fun provideCitiesDatabase(
        @ApplicationContext context: Context,
    ): FavoriteCitiesDatabase =
        databaseBuilder(
            context,
            FavoriteCitiesDatabase::class.java,
            "favorite_cities.db",
        ).build()
}
