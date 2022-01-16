package com.francescsoftware.weathersample.shared.mvi

import com.francescsoftware.weathersample.mvi.ReduceAction

interface Processor<R : ReduceAction> {
    fun handle(reduceAction: R)
}
