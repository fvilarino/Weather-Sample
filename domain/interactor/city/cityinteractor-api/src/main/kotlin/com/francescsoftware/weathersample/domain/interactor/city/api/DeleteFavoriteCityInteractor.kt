package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor

/** Deletes an entry from recent cities */
interface DeleteFavoriteCityInteractor : Interactor<DeleteFavoriteCityInteractor.Params, Unit> {

    /**
     * Configuration parameters for [DeleteFavoriteCityInteractor]
     *
     * @property cityId the id of the city to delete
     */
    data class Params(val cityId: Long)
}
