package com.francescsoftware.weathersample.shared.mvi

interface Dispatcher<A : Action> {
    fun dispatch(action: A)
}
