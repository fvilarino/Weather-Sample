package com.francescsoftware.weathersample.domain.interactor.city.api

import app.cash.turbine.Turbine
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeGetFavoriteCitiesInteractor : GetFavoriteCitiesInteractor() {
    val favoriteCities = Turbine<List<FavoriteCity>>()

    override fun buildStream(params: Unit): Flow<List<FavoriteCity>> = favoriteCities.asChannel().receiveAsFlow()
}
