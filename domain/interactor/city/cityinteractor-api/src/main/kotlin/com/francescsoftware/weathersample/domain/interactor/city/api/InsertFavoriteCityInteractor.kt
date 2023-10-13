package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor

/** Stores a [RecentCity] in persistent memory */
interface InsertFavoriteCityInteractor : Interactor<InsertFavoriteCityInteractor.Param, Unit> {

    data class Param(val city: FavoriteCity)
}
