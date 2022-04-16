package com.francescsoftware.weathersample.shared.mvi

import kotlinx.coroutines.CoroutineScope

/**
 * Used for asynchronous work
 */
interface Middleware<S : State, A : Action> : Reducer<S, A> {
    fun setup(scope: CoroutineScope, actionHandler: ActionHandler<A>)
}
