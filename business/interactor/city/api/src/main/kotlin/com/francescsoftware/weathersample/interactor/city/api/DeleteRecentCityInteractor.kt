package com.francescsoftware.weathersample.interactor.city.api

interface DeleteRecentCityInteractor {
    suspend fun execute(city: RecentCity)
}
