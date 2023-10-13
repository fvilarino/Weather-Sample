package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor

/** Deletes an entry from recent cities */
interface DeleteRecentCityInteractor : Interactor<DeleteRecentCityInteractor.Params, Unit> {
    data class Params(val city: RecentCity)
}
