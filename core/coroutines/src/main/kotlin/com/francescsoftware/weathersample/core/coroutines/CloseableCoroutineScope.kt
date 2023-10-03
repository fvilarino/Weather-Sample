package com.francescsoftware.weathersample.core.coroutines

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.io.Closeable
import javax.inject.Inject
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

/** [CoroutineScope] implementing [Closeable] that cancels the scope on closure. */
class CloseableCoroutineScope @Inject constructor(
    @MainCoroutineContext context: CoroutineContext,
) : Closeable, CoroutineScope {
    /** @{inheritDoc} */
    override val coroutineContext: CoroutineContext = context

    /** @{inheritDoc} */
    override fun close() = coroutineContext.cancel()
}

@Module
@ContributesTo(AppScope::class)
internal object CoroutineModule {
    @Provides
    @MainCoroutineContext
    fun provideCoroutineContext(
        dispatcherProvider: DispatcherProvider,
    ): CoroutineContext = SupervisorJob() + dispatcherProvider.main
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class MainCoroutineContext
