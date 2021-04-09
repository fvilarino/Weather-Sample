package com.francescsoftware.weathersample.presentation.shared.mvi

import androidx.annotation.UiThread

class ConsumableValue<T>(private val data: T) {

    private var consumed = false

    @UiThread
    fun consume(block: ConsumableValue<T>.(T) -> Unit) {
        val wasConsumed = consumed
        consumed = true
        if (!wasConsumed) {
            this.block(data)
        }
    }

    @UiThread
    fun ConsumableValue<T>.release() {
        consumed = false
    }
}
