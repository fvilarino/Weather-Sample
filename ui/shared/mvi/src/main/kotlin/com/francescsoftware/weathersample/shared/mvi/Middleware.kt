package com.francescsoftware.weathersample.shared.mvi

import kotlinx.coroutines.CoroutineScope

/** Handles asynchronous [Action] processing in the MVI framework */
abstract class Middleware<S : State, A : Action> {
    protected lateinit var dispatcher: Dispatcher<A>
    protected lateinit var scope: CoroutineScope

    /**
     * Processes the [Action]. This method should return quickly and queue any async work to run on a background thread.
     *
     * @param state - the current [State]
     * @param action - the [Action] to process
     */
    abstract fun process(state: S, action: A)

    protected fun dispatch(action: A) = dispatcher.dispatch(action)

    internal fun setup(
        scope: CoroutineScope,
        dispatcher: Dispatcher<A>,
    ) {
        this.dispatcher = dispatcher
        this.scope = scope
    }
}
