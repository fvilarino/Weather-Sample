package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor

/** Stores a [RecentCity] in persistent memory */
interface InsertRecentCityInteractor : Interactor<InsertRecentCityInteractor.Params, Unit> {

    data class Params(val city: RecentCity)
}
