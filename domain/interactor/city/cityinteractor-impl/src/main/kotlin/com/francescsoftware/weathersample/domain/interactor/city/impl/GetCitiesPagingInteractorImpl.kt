package com.francescsoftware.weathersample.domain.interactor.city.impl

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.type.location.Coordinates
import com.francescsoftware.weathersample.data.repository.city.api.CitiesPagingSourceFactory
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesPagingInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class GetCitiesPagingInteractorImpl @Inject constructor(
    private val citiesPagingSourceFactory: CitiesPagingSourceFactory,
) : GetCitiesPagingInteractor() {
    override fun buildStream(params: Parameters): Flow<PagingData<City>> = Pager(config = params.pagingConfig) {
        citiesPagingSourceFactory.create(
            params.toPagingSourceParams(),
        )
    }
        .flow
        .map { pagingData ->
            pagingData.map { city ->
                city.toCity()
            }
        }

    private fun Parameters.toPagingSourceParams() = when (this) {
        is Parameters.CoordinateParameters -> CitiesPagingSourceFactory.Params.Location(
            coordinates = Coordinates(
                longitude = coordinates.longitude,
                latitude = coordinates.latitude,
            ),
        )

        is Parameters.PrefixParameters -> CitiesPagingSourceFactory.Params.Prefix(
            prefix = prefix,
        )
    }
}
