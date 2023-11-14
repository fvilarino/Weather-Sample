package com.francescsoftware.weathersample.domain.interactor.city.api

import com.francescsoftware.weathersample.domain.interactor.foundation.StreamInteractor

/**
 * City model
 *
 * @property name - the name of the city
 */
@JvmInline
value class RecentCity(val name: String)

/** Loads the recent cities */
abstract class GetRecentCitiesInteractor : StreamInteractor<GetRecentCitiesInteractor.Params, List<RecentCity>>() {

    /**
     * Configuration parameters for [GetRecentCitiesInteractor]
     *
     * @property limit the max number of recent cities to fetch
     */
    data class Params(val limit: Int)
}
