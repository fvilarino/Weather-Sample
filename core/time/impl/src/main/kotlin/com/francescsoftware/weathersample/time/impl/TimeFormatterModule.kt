package com.francescsoftware.weathersample.time.impl

import com.francescsoftware.weathersample.time.api.TimeFormatter
import com.francescsoftware.weathersample.time.api.TimeParser
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
}
