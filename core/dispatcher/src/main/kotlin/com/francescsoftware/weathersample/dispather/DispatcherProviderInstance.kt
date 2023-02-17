package com.francescsoftware.weathersample.dispather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal object DispatcherProviderInstance : DispatcherProvider {

    private var dispatcher: CoroutineDispatcher? = null

    override val default: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Default

    override val io: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.IO

    override val main: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Main

    override val mainImmediate: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Main.immediate

    override val unconfined: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined
}
