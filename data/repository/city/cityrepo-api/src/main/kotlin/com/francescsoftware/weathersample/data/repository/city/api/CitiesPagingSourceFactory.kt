package com.francescsoftware.weathersample.data.repository.city.api

import androidx.paging.PagingSource
import com.francescsoftware.weathersample.data.repository.city.api.model.City

interface CitiesPagingSourceFactory {
    fun create(prefix: String): PagingSource<Int, City>
}
