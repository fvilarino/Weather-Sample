package com.francescsoftware.weathersample.ui.shared.mvi

import androidx.lifecycle.ViewModel
import com.francescsoftware.weathersample.core.coroutines.CloseableCoroutineScope
import kotlinx.coroutines.flow.StateFlow

/**
 * Base class for all [ViewModel]s following MVI design pattern.
 *
 * @param S - The [State] managed by this [ViewModel]
 * @param A - the [Action]s this [ViewModel] handles
 * @param closeableScope - a [CloseableCoroutineScope] to launch coroutines on
 * @param reducer - the [Reducer] that generates new state from the [State] and [Action]s
 * @param middlewares - a list of [Middleware] to handle [Action]s
 * @param initialState - the initial [State]]
 */
open class MviViewModel<S : State, A : Action>(
    closeableScope: CloseableCoroutineScope,
    reducer: Reducer<S, A>,
    middlewares: List<Middleware<S, A>> = emptyList(),
    initialState: S,
) : ViewModel(closeableScope) {

    private val stateReducer: StateReducerFlow<S, A> = stateReducerFlow(
        initialState = initialState,
        scope = closeableScope,
        reducer = reducer,
        middleware = middlewares,
    )

    /** The [State] managed by this [MviViewModel], as a [StateFlow] */
    val state: StateFlow<S> = stateReducer

    init {
        middlewares.forEach { middleware -> middleware.setup(closeableScope, stateReducer) }
    }

    protected fun handleAction(action: A) = stateReducer.dispatch(action)
}
