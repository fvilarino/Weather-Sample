package com.francescsoftware.weathersample.presentation.feature.navigator

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NavigatorModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator
}
