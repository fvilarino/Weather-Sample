package com.francescsoftware.weathersample.shared.mvi

abstract class Middleware<I : MviIntent, R : ReduceAction> {
    abstract val ordering: MiddlewareOrdering
    private lateinit var processor: Processor<R>

    abstract suspend fun executeIntent(intent: I)

    fun handle(reduceAction: R) = processor.handle(reduceAction)

    internal fun setProcessor(processor: Processor<R>) {
        this.processor = processor
    }
}
