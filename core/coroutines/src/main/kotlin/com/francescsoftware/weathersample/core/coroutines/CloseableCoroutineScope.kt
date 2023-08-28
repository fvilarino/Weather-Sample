package com.francescsoftware.weathersample.core.coroutines

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
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

/**
 * [CoroutineScope] implementing [Closeable] that cancels the scope on
 * closure.
 */
class CloseableCoroutineScope @Inject constructor(
    @MainCoroutineContext context: CoroutineContext,
) : Closeable, CoroutineScope {
    /** @{inheritDoc} */
    override val coroutineContext: CoroutineContext = context

    /** @{inheritDoc} */
    override fun close() = coroutineContext.cancel()
}

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineModule {
    @Provides
    @MainCoroutineContext
    fun provideCoroutineContext(
        dispatcherProvider: DispatcherProvider
    ): CoroutineContext = SupervisorJob() + dispatcherProvider.main
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class MainCoroutineContext
