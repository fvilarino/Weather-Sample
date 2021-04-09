package com.francescsoftware.weathersample.presentation.shared.mvi

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest

class MviLifecycleObserver<S : State, E : Event, I : MviIntent>(
    private val mviView: MviView<S, E>,
    private val viewModel: MviViewModel<S, E, I, out ReduceAction>
) : DefaultLifecycleObserver {

    val currentState: S
        get() = viewModel.state.value

    override fun onCreate(owner: LifecycleOwner) {
        owner.lifecycleScope.launchWhenStarted {
            viewModel
                .state
                .collectLatest { state -> mviView.onState(state) }
        }
        owner.lifecycleScope.launchWhenStarted {
            viewModel
                .event
                .collectLatest { consumable ->
                    consumable.consume { event ->
                        mviView.onEvent(event)
                    }
                }
        }
    }
}
