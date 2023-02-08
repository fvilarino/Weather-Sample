package com.francescsoftware.weathersample.shared.mvi

import androidx.lifecycle.ViewModel
import com.francescsoftware.weathersample.coroutines.CloseableCoroutineScope
import kotlinx.coroutines.flow.StateFlow

abstract class MviViewModel<S : State, A : Action>(
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

    val state: StateFlow<S> = stateReducer

    init {
        middlewares.forEach { middleware -> middleware.setup(closeableScope, stateReducer) }
    }

    protected fun handleAction(action: A) = stateReducer.dispatch(action)
}
