package com.francescsoftware.weathersample.shared.mvi

import kotlinx.coroutines.CoroutineScope

/**
 * Used for asynchronous work
 */
abstract class Middleware<S : State, A : Action> : Reducer<S, A> {
    protected lateinit var actionHandler: ActionHandler<A>
    protected lateinit var scope: CoroutineScope

    fun setup(
        scope: CoroutineScope,
        actionHandler: ActionHandler<A>,
    ) {
        this.actionHandler = actionHandler
        this.scope = scope
    }
}
