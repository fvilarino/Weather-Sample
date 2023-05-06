package com.francescsoftware.weathersample.interactor.city.impl

import com.francescsoftware.weathersample.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.interactor.city.api.InsertRecentCityInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface CityInteractorModule {
    @Binds
    fun bindGetCitiesInteractor(
        getCitiesInteractorImpl: GetCitiesInteractorImpl
    ): GetCitiesInteractor

    @Binds
    fun bindGetRecentCitiesInteractor(
        getRecentCitiesInteractorImpl: GetRecentCitiesInteractorImpl
    ): GetRecentCitiesInteractor

    @Binds
    fun bindInsertRecentCityInteractor(
        insertRecentCityInteractorImpl: InsertRecentCityInteractorImpl
    ): InsertRecentCityInteractor

    @Binds
    fun bindDeleteRecentCityInteractor(
        deleteRecentCityInteractorImpl: DeleteRecentCityInteractorImpl
    ): DeleteRecentCityInteractor
}
