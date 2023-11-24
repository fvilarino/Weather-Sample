package com.francescsoftware.weathersample.domain.interactor.city.api

import androidx.paging.PagingConfig
import com.francescsoftware.weathersample.core.type.location.Coordinates
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.foundation.PagingInteractor

/** Loads paginated cities */
abstract class GetCitiesPagingInteractor : PagingInteractor<GetCitiesPagingInteractor.Parameters, City>() {

    sealed interface Parameters : PagingInteractor.Parameters {
        /**
         * Configuration parameters for [GetCitiesPagingInteractor]
         *
         * @property pagingConfig the [PagingConfig] for the data load
         * @property prefix the city prefix for the search query
         */
        data class PrefixParameters(
            override val pagingConfig: PagingConfig,
            val prefix: String,
        ) : Parameters

        /**
         * Configuration parameters for [GetCitiesPagingInteractor]
         *
         * @property pagingConfig the [PagingConfig] for the data load
         * @property coordinates the coordinates for the cities load request
         */
        data class CoordinateParameters(
            override val pagingConfig: PagingConfig,
            val coordinates: Coordinates,
        ) : Parameters
    }
}
