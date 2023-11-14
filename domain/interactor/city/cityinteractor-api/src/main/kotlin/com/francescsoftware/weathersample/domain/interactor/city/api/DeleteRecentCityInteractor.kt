package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor

/** Deletes an entry from recent cities */
interface DeleteRecentCityInteractor : Interactor<DeleteRecentCityInteractor.Params, Unit> {

    /**
     * Configuration parameters for [DeleteRecentCityInteractor]
     *
     * @property city the id of the recent city to delete
     */
    data class Params(val city: RecentCity)
}
