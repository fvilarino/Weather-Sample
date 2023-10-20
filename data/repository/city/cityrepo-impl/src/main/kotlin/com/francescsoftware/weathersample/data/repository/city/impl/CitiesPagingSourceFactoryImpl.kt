package com.francescsoftware.weathersample.data.repository.city.impl

import androidx.paging.PagingSource
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.data.repository.city.api.CitiesPagingSourceFactory
import com.francescsoftware.weathersample.data.repository.city.api.CityRepository
import com.francescsoftware.weathersample.data.repository.city.api.model.City
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class CitiesPagingSourceFactoryImpl @Inject constructor(
    private val cityRepository: CityRepository,
) : CitiesPagingSourceFactory {
    override fun create(prefix: String): PagingSource<Int, City> = CitiesPagingSource(
        prefix = prefix,
        cityRepository = cityRepository,
    )
}
