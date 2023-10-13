package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor

/** Deletes an entry from recent cities */
interface DeleteFavoriteCityInteractor : Interactor<DeleteFavoriteCityInteractor.Params, Unit> {
    data class Params(val city: FavoriteCity)
}
