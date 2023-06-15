package com.francescsoftware.weathersample.core.connectivity.impl

import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ConnectivityMonitorModule {
    @Binds
    @Singleton
    fun bindConnectivityMonitor(connectivityMonitorImpl: ConnectivityMonitorImpl): ConnectivityMonitor
}
