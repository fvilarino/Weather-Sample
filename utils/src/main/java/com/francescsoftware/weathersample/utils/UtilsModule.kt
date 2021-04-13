package com.francescsoftware.weathersample.utils

import com.francescsoftware.weathersample.utils.dispatcher.DispatcherProvider
import com.francescsoftware.weathersample.utils.dispatcher.DispatcherProviderImpl
import com.francescsoftware.weathersample.utils.time.TimeFormatter
import com.francescsoftware.weathersample.utils.time.TimeFormatterImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {
    @Binds
    @Reusable
    abstract fun bindTimeFormatter(
        timeFormatterImpl: TimeFormatterImpl
    ): TimeFormatter

    @Binds
    @Singleton
    abstract fun bindDispacherProvider(
        dispatcherProviderImpl: DispatcherProviderImpl
    ): DispatcherProvider
}
