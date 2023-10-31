package com.francescsoftware.weathersample.domain.interactor.city.api

import app.cash.turbine.Turbine

class FakeDeleteRecentCityInteractor : DeleteRecentCityInteractor {
    val cities = Turbine<RecentCity>()

    override suspend fun invoke(params: DeleteRecentCityInteractor.Params) {
        cities.add(params.city)
    }
}
