package com.francescsoftware.weathersample.domain.interactor.city.api

import app.cash.turbine.Turbine

class FakeDeleteFavoriteCityInteractor : DeleteFavoriteCityInteractor {
    val cities = Turbine<Long>()

    override suspend fun invoke(params: DeleteFavoriteCityInteractor.Params) {
        cities.add(params.cityId)
    }
}
