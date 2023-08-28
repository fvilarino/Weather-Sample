package com.francescsoftware.weathersample.ui.shared.mvi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.ViewModel
import com.francescsoftware.weathersample.core.coroutines.CloseableCoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val BufferSize = 64

/**
 * Base class for all [ViewModel]s adhering to the MVI framework
 *
 * @param S - The [State] managed by this [ViewModel]
 * @param A - the [Action]s this [ViewModel] handles
 * @param closeableScope - a [CloseableCoroutineScope] to launch coroutines on
 * @param reducer - the [Reducer] that generates new state from the current
 *     [State] and [Action]s
 * @param middlewares - a list of [Middleware] to handle [Action]s
 * @param initialState - the initial [State]
 */
open class MviViewModel<S : State, A : Action>(
    private val closeableScope: CloseableCoroutineScope,
    private val reducer: Reducer<S, A>,
    private val middlewares: List<Middleware<S, A>> = emptyList(),
    initialState: S,
) : ViewModel(closeableScope), Dispatcher<A> {

    private data class ActionImpl<S : State, A : Action>(
        val state: S,
        val action: A,
    )

    private val actions = MutableSharedFlow<ActionImpl<S, A>>(extraBufferCapacity = BufferSize)

    /** The [State] managed by this [MviViewModel] */
    var state: S by mutableStateOf(initialState)
        private set

    init {
        middlewares.fastForEach { middleware -> middleware.setDispatcher(this) }
        closeableScope.launch {
            actions
                .onEach { actionImpl ->
                    middlewares.fastForEach { middleware ->
                        middleware.process(actionImpl.state, actionImpl.action)
                    }
                }
                .collect()
        }
        closeableScope.launch {
            actions.collect {
                state = reducer.reduce(state, it.action)
            }
        }
    }

    override fun dispatch(action: A) {
        closeableScope.launch {
            actions.emit(ActionImpl(state, action))
        }
    }

    override fun onCleared() {
        super.onCleared()
        middlewares.fastForEach { middleware -> middleware.onCleared() }
    }
}
