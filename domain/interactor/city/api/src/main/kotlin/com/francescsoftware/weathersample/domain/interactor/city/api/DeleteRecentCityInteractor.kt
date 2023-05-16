package com.francescsoftware.weathersample.domain.interactor.city.api

/** Deletes an entry from recent cities */
interface DeleteRecentCityInteractor {
    /**
     * Deletes a [RecentCity]
     *
     * @param city - the [RecentCity] to delete
     */
    suspend operator fun invoke(city: RecentCity)
}
