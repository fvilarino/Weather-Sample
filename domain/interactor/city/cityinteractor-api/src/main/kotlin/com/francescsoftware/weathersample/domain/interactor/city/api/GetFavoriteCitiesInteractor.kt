package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.domain.interactor.foundation.StreamInteractor

/** Loads the favorite cities */
abstract class GetFavoriteCitiesInteractor :
    StreamInteractor<GetFavoriteCitiesInteractor.Params, List<FavoriteCity>>() {

    data object Params
}
