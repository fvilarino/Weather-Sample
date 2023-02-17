package com.francescsoftware.weathersample.dispather

import kotlinx.coroutines.CoroutineDispatcher

/** Provides the [CoroutineDispatcher]s to use in the app. */
interface DispatcherProvider {

    /** [CoroutineDispatcher] for CPU bound tasks. */
    val default: CoroutineDispatcher

    /** [CoroutineDispatcher] for IO bound tasks. */
    val io: CoroutineDispatcher

    /** [CoroutineDispatcher] for UI bound tasks. */
    val main: CoroutineDispatcher

    /** [CoroutineDispatcher] for CPU bound tasks, with immediate dispatching. */
    val mainImmediate: CoroutineDispatcher

    /** [CoroutineDispatcher] that is not confined to a specific thread. */
    val unconfined: CoroutineDispatcher
}
