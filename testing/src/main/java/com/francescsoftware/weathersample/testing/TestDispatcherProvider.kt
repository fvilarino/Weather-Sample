package com.francescsoftware.weathersample.testing

import com.francescsoftware.weathersample.dispather.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val testDispatcherProvider = object : DispatcherProvider {
    private var dispatcher: CoroutineDispatcher? = null

    override val main: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined
    override val mainImmediate: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined
    override val io: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined
    override val default: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined
    override val unconfined: CoroutineDispatcher
        get() = dispatcher ?: Dispatchers.Unconfined

    override fun setDispatcher(dispatcher: CoroutineDispatcher?) {
        this.dispatcher = dispatcher
    }
}
