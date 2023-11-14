package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Cities
import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor

/** Gets a list of cities matching the prefix */
interface GetCitiesInteractor : Interactor<GetCitiesInteractor.Params, Either<Cities>> {

    /**
     * Configuration parameters for [GetCitiesInteractor]
     *
     * @param prefix prefix to filter cities
     * @param limit max number of results to return
     */
    data class Params(
        val prefix: String,
        val limit: Int = 10,
    )
}
