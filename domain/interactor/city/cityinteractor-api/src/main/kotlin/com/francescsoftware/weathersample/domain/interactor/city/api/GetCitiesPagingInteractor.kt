package com.francescsoftware.weathersample.domain.interactor.city.api

import androidx.paging.PagingConfig
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.foundation.PagingInteractor

/** Loads paginated cities */
abstract class GetCitiesPagingInteractor : PagingInteractor<GetCitiesPagingInteractor.Parameters, City>() {

    data class Parameters(
        override val pagingConfig: PagingConfig,
        val prefix: String,
    ) : PagingInteractor.Parameters
}
