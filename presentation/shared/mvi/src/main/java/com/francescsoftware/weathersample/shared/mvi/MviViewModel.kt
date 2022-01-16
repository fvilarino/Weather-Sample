package com.francescsoftware.weathersample.mvi

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francescsoftware.weathersample.dispather.DispatcherProviderInstance
import com.francescsoftware.weathersample.shared.mvi.Middleware
import com.francescsoftware.weathersample.shared.mvi.MiddlewareOrdering
import com.francescsoftware.weathersample.shared.mvi.Processor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

interface State

interface Event

interface MviIntent

interface ReduceAction

private const val TAG = "MviViewModel"
private const val FLOW_BUFFER_CAPACITY = 64

abstract class MviViewModel<S : State, E : Event, I : MviIntent, R : ReduceAction>(
    initialState: S
) : ViewModel(), Processor<R> {

    private val _state = mutableStateOf(initialState)
    private val _events = MutableSharedFlow<E>(extraBufferCapacity = FLOW_BUFFER_CAPACITY)
    protected val currentState: S
        get() = _state.value

    val state: androidx.compose.runtime.State<S> = _state
    val events: Flow<E> = _events.asSharedFlow()
    private val intents = MutableSharedFlow<I>(extraBufferCapacity = FLOW_BUFFER_CAPACITY)
    private val reduceActions = MutableSharedFlow<R>(extraBufferCapacity = FLOW_BUFFER_CAPACITY)

    protected open val middleware: List<Middleware<I, R>> = emptyList()

    private val preMiddleware: List<Middleware<I, R>> by lazy(LazyThreadSafetyMode.NONE) {
        middleware.filter { middleware ->
            middleware.ordering == MiddlewareOrdering.BEFORE_VIEWMODEL
        }
    }

    private val postMiddleware: List<Middleware<I, R>> by lazy(LazyThreadSafetyMode.NONE) {
        middleware.filter { middleware ->
            middleware.ordering == MiddlewareOrdering.AFTER_VIEWMODEL
        }
    }

    init {
        intents
            .onEach { intent ->
                Timber.tag(TAG).v("Processing [$intent]")
                preMiddleware.forEach { middleware -> middleware.executeIntent(intent) }
                executeIntent(intent)
                postMiddleware.forEach { middleware -> middleware.executeIntent(intent) }
            }
            .launchIn(viewModelScope)
        reduceActions
            .onEach { action ->
                Timber.tag(TAG).v("Reducing [$action]")
                _state.value = reduce(_state.value, action)
            }
            .launchIn(viewModelScope)

        // run after constructor
        viewModelScope.launch {
            middleware.forEach { middleware -> middleware.setProcessor(this@MviViewModel) }
        }
    }

    fun onIntent(intent: I) {
        intents.tryEmit(intent)
    }

    fun onEvent(event: E) {
        _events.tryEmit(event)
    }

    override fun handle(reduceAction: R) {
        reduceActions.tryEmit(reduceAction)
    }

    protected fun onBackground(
        block: suspend CoroutineScope.() -> Unit,
    ) {
        viewModelScope.launch(
            context = DispatcherProviderInstance.default,
            block = block,
        )
    }

    protected abstract suspend fun executeIntent(intent: I)

    protected abstract fun reduce(state: S, reduceAction: R): S
}
