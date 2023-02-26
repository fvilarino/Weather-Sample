package com.francescsoftware.weathersample.dispatcher

import com.francescsoftware.weathersample.dispather.DispatcherProvider
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Dispatcher provider to use in tests.
 *
 * @property coroutineContext the coroutine context to use for all dispatchers, defaults to [EmptyCoroutineContext]
 */
class TestDispatcherProvider(
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext,
) : DispatcherProvider {
    override val default: CoroutineContext
        get() = coroutineContext
    override val io: CoroutineContext
        get() = coroutineContext
    override val main: CoroutineContext
        get() = coroutineContext
    override val mainImmediate: CoroutineContext
        get() = coroutineContext
    override val unconfined: CoroutineContext
        get() = coroutineContext
}
