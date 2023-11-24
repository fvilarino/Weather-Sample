package com.francescsoftware.weathersample.data.repository.city.api

import androidx.paging.PagingSource
import com.francescsoftware.weathersample.core.type.location.Coordinates
import com.francescsoftware.weathersample.data.repository.city.api.model.City

interface CitiesPagingSourceFactory {
    fun create(params: Params): PagingSource<Int, City>

    sealed interface Params {
        data class Prefix(val prefix: String) : Params
        data class Location(val coordinates: Coordinates) : Params
    }
}
