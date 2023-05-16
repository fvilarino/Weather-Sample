package com.francescsoftware.weathersample.ui.shared.mvi

/** Used for synchronous updates of the [State] */
interface Reducer<S : State, A : Action> {
    /**
     * Generates a new instance of the [State] based on the [Action]
     *
     * @param state - the current [State]
     * @param action - the [Action] to reduce the [State] with
     * @return the reduced [State]
     */
    fun reduce(state: S, action: A): S
}
