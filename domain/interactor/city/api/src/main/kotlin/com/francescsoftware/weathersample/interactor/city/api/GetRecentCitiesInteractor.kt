package com.francescsoftware.weathersample.interactor.city.api

import kotlinx.coroutines.flow.Flow

/**
 * City model
 *
 * @property name - the name of the city
 */
@JvmInline
value class RecentCity(val name: String)

/** Loads the recent cities */
interface GetRecentCitiesInteractor {
    /**
     * Loads the recent cities
     *
     * @param limit - max number of cities to load
     * @return a [Flow] of [List] of [RecentCity]
     */
    operator fun invoke(limit: Int): Flow<List<RecentCity>>
}
