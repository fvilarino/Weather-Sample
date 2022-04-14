package com.francescsoftware.weathersample.shared.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val BufferSize = 64

interface StateReducerFlow<S : State, A : Action> : StateFlow<S> {
    fun asStateFlow(): StateFlow<S>

    fun handleAction(action: A)
}

fun <S : State, A : Action> ViewModel.stateReducerFlow(
    initialState: S,
    reducer: (S, A) -> S,
): StateReducerFlow<S, A> = StateReducerFlowImpl(
    initialState,
    reducer,
    viewModelScope,
)

private class StateReducerFlowImpl<S : State, A : Action>(
    initialState: S,
    reducer: (S, A) -> S,
    private val scope: CoroutineScope,
) : StateReducerFlow<S, A> {

    private val actions = MutableSharedFlow<A>(extraBufferCapacity = BufferSize)

    private val stateFlow = actions
        .runningFold(initialState, reducer)
        .stateIn(
            scope = scope,
            started = Eagerly,
            initialValue = initialState,
        )

    override val replayCache get() = stateFlow.replayCache

    override val value get() = stateFlow.value

    override suspend fun collect(
        collector: FlowCollector<S>
    ): Nothing {
        stateFlow.collect(collector)
    }

    override fun asStateFlow(): StateFlow<S> = this

    override fun handleAction(action: A) {
        scope.launch { actions.emit(action) }
    }
}
