package com.francescsoftware.weathersample.domain.interactor.foundation

/**
 * One shot interactor
 *
 * @param P parameters to configure the interactor execution
 * @param T the returned type
 */
interface Interactor<P : Any, T> {

    suspend operator fun invoke(params: P): T
}
