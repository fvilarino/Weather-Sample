package com.francescsoftware.weathersample.interactor.city.api

/** Stores a [RecentCity] in persistent memory */
interface InsertRecentCityInteractor {

    /**
     * Stores a [RecentCity]
     *
     * @param city - the city to store
     */
    suspend fun execute(city: RecentCity)
}
