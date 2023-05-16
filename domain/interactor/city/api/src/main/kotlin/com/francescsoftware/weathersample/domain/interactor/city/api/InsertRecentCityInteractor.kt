package com.francescsoftware.weathersample.domain.interactor.city.api

/** Stores a [RecentCity] in persistent memory */
interface InsertRecentCityInteractor {

    /**
     * Stores a [RecentCity]
     *
     * @param city - the city to store
     */
    suspend operator fun invoke(city: RecentCity)
}
