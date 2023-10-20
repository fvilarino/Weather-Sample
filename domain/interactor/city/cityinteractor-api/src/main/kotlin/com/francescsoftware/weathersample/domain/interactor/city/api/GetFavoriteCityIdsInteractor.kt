package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.foundation.StreamInteractor

abstract class GetFavoriteCityIdsInteractor : StreamInteractor<Unit, Set<Long>>()
