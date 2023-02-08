package com.francescsoftware.weathersample.shared.mvi

/**
 * Used for synchronous update of the state
 */
interface Reducer<S : State, A : Action> {
    fun reduce(state: S, action: A): S
}
