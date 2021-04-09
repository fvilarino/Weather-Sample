package com.francescsoftware.weathersample.presentation.shared.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

interface State

interface Event

interface MviIntent

interface ReduceAction

private const val TAG = "MviViewModel"
private const val FLOW_BUFFER_CAPACITY = 64

abstract class MviViewModel<S : State, E : Event, I : MviIntent, R : ReduceAction>(
    initialState: S
) : ViewModel() {

    private val stateFlow = MutableStateFlow<S>(initialState)
    protected val currentState: S
        get() = stateFlow.value

    private val _event = MutableStateFlow<ConsumableValue<E>?>(null)
    val state: StateFlow<S> = stateFlow.asStateFlow()
    val event: Flow<ConsumableValue<E>> = _event.filterNotNull()
    private val intentFlow = MutableSharedFlow<I>(extraBufferCapacity = FLOW_BUFFER_CAPACITY)
    private val reduceFlow = MutableSharedFlow<R>(extraBufferCapacity = FLOW_BUFFER_CAPACITY)

    init {
        intentFlow
            .onEach { intent ->
                Timber.tag(TAG).v("Processing [$intent]")
                executeIntent(intent)
            }
            .launchIn(viewModelScope)
        reduceFlow
            .onEach { action ->
                Timber.tag(TAG).v("Reducing [$action]")
                stateFlow.value = reduce(stateFlow.value, action)
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: I) {
        intentFlow.tryEmit(intent)
    }

    fun onEvent(event: E) {
        _event.value = ConsumableValue(event)
    }

    protected fun handle(reduceAction: R) {
        reduceFlow.tryEmit(reduceAction)
    }

    protected abstract suspend fun executeIntent(intent: I)

    protected abstract fun reduce(state: S, reduceAction: R): S
}
