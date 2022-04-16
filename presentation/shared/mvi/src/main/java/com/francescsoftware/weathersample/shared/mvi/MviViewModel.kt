package com.francescsoftware.weathersample.shared.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow

abstract class MviViewModel<S : State, A : Action>(
    reducer: Reducer<S, A>,
    middlewares: List<Middleware<S, A>> = emptyList(),
    initialState: S,
) : ViewModel() {

    private val stateReducer: StateReducerFlow<S, A> = stateReducerFlow(
        initialState = initialState,
        reducer = reducer,
        middleware = middlewares,
    )

    val state: StateFlow<S> = stateReducer

    init {
        middlewares.forEach { middleware -> middleware.setup(viewModelScope, stateReducer) }
    }

    protected fun handleAction(action: A) = stateReducer.handleAction(action)
}
