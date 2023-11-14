package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor

/** Stores a [RecentCity] in persistent memory */
interface InsertRecentCityInteractor : Interactor<InsertRecentCityInteractor.Params, Unit> {

    /**
     * Configuration parameters for [InsertRecentCityInteractor]
     *
     * @property city the [RecentCity] to store
     */
    data class Params(val city: RecentCity)
}
