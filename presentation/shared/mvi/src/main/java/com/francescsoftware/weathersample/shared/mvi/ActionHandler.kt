package com.francescsoftware.weathersample.shared.mvi

interface ActionHandler<A : Action> {
    fun handleAction(action: A)
}
