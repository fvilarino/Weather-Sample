package com.francescsoftware.weathersample

import android.app.Application
import android.content.Context
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.ApplicationContext
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.ui.feature.home.di.ActivityComponent
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeComponent
import dagger.Binds
import dagger.BindsInstance
import dagger.Module

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
interface ApplicationComponent {
    fun getActivityComponentFactory(): ActivityComponent.Factory

    @MergeComponent.Factory
    fun interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}

@Module
@ContributesTo(AppScope::class)
interface AppModule {
    @Binds
    @ApplicationContext
    @SingleIn(AppScope::class)
    fun bindApplicationContext(application: Application): Context
}
