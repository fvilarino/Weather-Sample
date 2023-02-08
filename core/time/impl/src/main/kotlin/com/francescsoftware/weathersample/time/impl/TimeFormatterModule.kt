package com.francescsoftware.weathersample.time.impl

import com.francescsoftware.weathersample.time.api.TimeFormatter
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeFormatterModule {
    @Binds
    @Reusable
    internal abstract fun bindTimeFormatter(
        timeFormatterImpl: TimeFormatterImpl
    ): TimeFormatter
}
