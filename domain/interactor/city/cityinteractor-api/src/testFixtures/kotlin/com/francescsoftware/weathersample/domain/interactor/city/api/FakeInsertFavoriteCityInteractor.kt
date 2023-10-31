package com.francescsoftware.weathersample.domain.interactor.city.api

import app.cash.turbine.Turbine
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity

class FakeInsertFavoriteCityInteractor : InsertFavoriteCityInteractor {
    val cities = Turbine<FavoriteCity>()

    override suspend fun invoke(params: InsertFavoriteCityInteractor.Param) {
        cities.add(params.city)
    }
}
