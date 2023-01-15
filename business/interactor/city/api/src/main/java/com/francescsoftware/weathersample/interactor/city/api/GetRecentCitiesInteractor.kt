package com.francescsoftware.weathersample.interactor.city.api

import kotlinx.coroutines.flow.Flow

@JvmInline
value class RecentCity(val name: String)

interface GetRecentCitiesInteractor {
    fun execute(limit: Int): Flow<List<RecentCity>>
}
