package com.francescsoftware.weathersample.domain.interactor.city.api

import app.cash.turbine.Turbine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetFavoriteCityIdsInteractor : GetFavoriteCityIdsInteractor() {
    val favoriteCities = Turbine<Set<Long>>()

    override fun buildStream(params: Unit): Flow<Set<Long>> = flow {
        favoriteCities.awaitItem()
    }
}
