package com.francescsoftware.weathersample.domain.interactor.foundation

import androidx.paging.PagingConfig
import androidx.paging.PagingData

/**
 * Base interactor for paging data
 *
 * @param P [Parameters] configuring this interactor
 * @param T the returned data type
 */
abstract class PagingInteractor<P : PagingInteractor.Parameters, T : Any> : StreamInteractor<P, PagingData<T>>() {
    interface Parameters {
        val pagingConfig: PagingConfig
    }
}
