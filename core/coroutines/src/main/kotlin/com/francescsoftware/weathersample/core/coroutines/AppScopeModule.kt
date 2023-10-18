package com.francescsoftware.weathersample.core.coroutines

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Module
@ContributesTo(AppScope::class)
object AppScopeModule {
    @Provides
    @SingleIn(AppScope::class)
    @ApplicationScope
    fun providesCoroutineScope(
        dispatcherProvider: DispatcherProvider,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcherProvider.default)
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
