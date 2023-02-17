package com.francescsoftware.weathersample.testing.mock

import com.francescsoftware.weathersample.dispather.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/** {@inheritDoc} */
val testDispatcherProvider = object : DispatcherProvider {
    private var dispatcher: CoroutineDispatcher? = null

    /** {@inheritDoc} */
    override val main: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined

    /** {@inheritDoc} */
    override val mainImmediate: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined

    /** {@inheritDoc} */
    override val io: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined

    /** {@inheritDoc} */
    override val default: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined

    /** {@inheritDoc} */
    override val unconfined: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined
}
