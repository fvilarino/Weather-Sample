package com.francescsoftware.weathersample.shared.mvi

interface Processor<R : ReduceAction> {
    fun handle(reduceAction: R)
}
