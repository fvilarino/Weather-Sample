package com.francescsoftware.weathersample.domain.interactor.city.api

import app.cash.turbine.Turbine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeGetRecentCitiesInteractor : GetRecentCitiesInteractor() {
    val cities = Turbine<List<RecentCity>>()

    override fun buildStream(params: Params): Flow<List<RecentCity>> = cities.asChannel().receiveAsFlow()
}
