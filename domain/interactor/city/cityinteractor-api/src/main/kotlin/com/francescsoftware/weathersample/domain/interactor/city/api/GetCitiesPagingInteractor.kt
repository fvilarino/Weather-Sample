package com.francescsoftware.weathersample.domain.interactor.city.api

import androidx.paging.PagingConfig
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.foundation.PagingInteractor

/** Loads paginated cities */
abstract class GetCitiesPagingInteractor : PagingInteractor<GetCitiesPagingInteractor.Parameters, City>() {

    /**
     * Configuration parameters for [GetCitiesPagingInteractor]
     *
     * @property pagingConfig the [PagingConfig] for the data load
     * @property prefix the city prefix for the search query
     */
    data class Parameters(
        override val pagingConfig: PagingConfig,
        val prefix: String,
    ) : PagingInteractor.Parameters
}
