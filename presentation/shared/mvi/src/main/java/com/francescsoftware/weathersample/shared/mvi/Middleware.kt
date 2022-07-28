package com.francescsoftware.weathersample.shared.mvi

import kotlinx.coroutines.CoroutineScope

/**
 * Used for asynchronous work
 */
abstract class Middleware<S : State, A : Action> {
    protected lateinit var dispatcher: Dispatcher<A>
    protected lateinit var scope: CoroutineScope

    abstract fun process(state: S, action: A)

    internal fun setup(
        scope: CoroutineScope,
        dispatcher: Dispatcher<A>,
    ) {
        this.dispatcher = dispatcher
        this.scope = scope
    }
}
