package com.francescsoftware.weathersample.domain.interactor.city.api

import androidx.paging.PagingData
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeGetCitiesPagingInteractor : GetCitiesPagingInteractor() {
    val flow = MutableStateFlow<PagingData<City>>(
        PagingData.from(emptyList())
    )

    override fun buildStream(params: Parameters): Flow<PagingData<City>> {
        return flow
    }
}
