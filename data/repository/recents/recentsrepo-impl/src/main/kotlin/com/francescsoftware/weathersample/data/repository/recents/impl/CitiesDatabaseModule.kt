package com.francescsoftware.weathersample.data.repository.recents.impl

import android.content.Context
import androidx.room.Room
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.ApplicationContext
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.data.repository.recents.impl.dao.RecentCitiesDatabase
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(AppScope::class)
object CitiesDatabaseModule {
    @Provides
    @SingleIn(AppScope::class)
    internal fun provideCitiesDatabase(
        @ApplicationContext context: Context,
    ): RecentCitiesDatabase =
        Room.databaseBuilder(
            context,
            RecentCitiesDatabase::class.java,
            "recent_cities.db",
        ).build()
}
