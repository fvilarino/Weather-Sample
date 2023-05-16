package com.francescsoftware.weathersample.core.dispather

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

/** Provides the [CoroutineDispatcher]s to use in the app. */
interface DispatcherProvider {

    /** [CoroutineDispatcher] for CPU bound tasks. */
    val default: CoroutineContext

    /** [CoroutineDispatcher] for IO bound tasks. */
    val io: CoroutineContext

    /** [CoroutineDispatcher] for UI bound tasks. */
    val main: CoroutineContext

    /** [CoroutineDispatcher] for CPU bound tasks, with immediate dispatching. */
    val mainImmediate: CoroutineContext

    /** [CoroutineDispatcher] that is not confined to a specific thread. */
    val unconfined: CoroutineContext
}
