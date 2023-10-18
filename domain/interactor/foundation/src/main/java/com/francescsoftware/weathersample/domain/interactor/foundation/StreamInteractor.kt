package com.francescsoftware.weathersample.domain.interactor.foundation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

/**
 * Stream interactor, returning a [Flow] of items
 *
 * @param P parameters to configure the interactor execution
 * @param T the type of the returned [Flow]
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class StreamInteractor<P, T> {

    private val paramsFlow = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    val stream: Flow<T> = paramsFlow
        .distinctUntilChanged()
        .flatMapLatest { buildStream(it) }
        .distinctUntilChanged()

    operator fun invoke(params: P) {
        paramsFlow.tryEmit(params)
    }

    protected abstract fun buildStream(params: P): Flow<T>
}
