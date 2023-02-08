package com.francescsoftware.weathersample.dispather

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val mainImmediate: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

    fun setDispatcher(dispatcher: CoroutineDispatcher?)
}
