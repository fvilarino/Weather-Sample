package com.francescsoftware.weathersample.shared.mvi

/** Dispatches [Action]s to be handled by the MVI framework */
interface Dispatcher<A : Action> {
    /**
     * Dispatches an [Action] to be processed by the MVI framework
     *
     * @param action - the [Action] to process
     */
    fun dispatch(action: A)
}
