package com.francescsoftware.weathersample.core.time.impl

import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import com.francescsoftware.weathersample.core.time.api.TimeParser
import com.francescsoftware.weathersample.core.time.api.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface TimeFormatterModule {
    @Binds
    @Reusable
    fun bindTimeFormatter(
        timeFormatterImpl: TimeFormatterImpl
    ): TimeFormatter

    @Binds
    @Reusable
    fun bindTimeParser(
        timeParserImpl: TimeParserImpl
    ): TimeParser

    @Binds
    @Reusable
    fun bindTimeProvider(
        timeProviderImpl: TimeProviderImpl
    ): TimeProvider
}
