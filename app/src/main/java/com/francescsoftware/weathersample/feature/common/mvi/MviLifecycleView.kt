package com.francescsoftware.weathersample.feature.common.mvi

import androidx.lifecycle.LifecycleOwner

interface MviLifecycleView<S : State, E : Event> : MviView<S, E>, LifecycleOwner
