package com.francescsoftware.weathersample.interactor.city.api

interface InsertRecentCityInteractor {
    suspend fun execute(city: RecentCity)
}
