package com.francescsoftware.weathersample.domain.interactor.city.api

import app.cash.turbine.Turbine

class FakeInsertRecentCityInterator : InsertRecentCityInteractor {
    val cities = Turbine<RecentCity>()

    override suspend fun invoke(params: InsertRecentCityInteractor.Params) {
        cities.add(params.city)
    }
}
