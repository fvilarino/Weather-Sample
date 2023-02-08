package com.francescsoftware.weathersample.coroutines

import com.francescsoftware.weathersample.dispather.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.io.Closeable
import javax.inject.Inject
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

class CloseableCoroutineScope @Inject constructor(
    @MainImmediateCoroutineContext context: CoroutineContext,
) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() = coroutineContext.cancel()
}

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineModule {
    @Provides
    @MainImmediateCoroutineContext
    fun provideCoroutineContext(
        dispatcherProvider: DispatcherProvider
    ): CoroutineContext = SupervisorJob() + dispatcherProvider.mainImmediate
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class MainImmediateCoroutineContext
