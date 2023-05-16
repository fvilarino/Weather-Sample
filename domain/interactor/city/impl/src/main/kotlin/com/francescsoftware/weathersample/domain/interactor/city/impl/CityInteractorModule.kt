package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertRecentCityInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface CityInteractorModule {
    @Binds
    fun bindGetCitiesInteractor(
        getCitiesInteractorImpl: GetCitiesInteractorImpl,
    ): GetCitiesInteractor

    @Binds
    fun bindGetRecentCitiesInteractor(
        getRecentCitiesInteractorImpl: GetRecentCitiesInteractorImpl,
    ): GetRecentCitiesInteractor

    @Binds
    fun bindInsertRecentCityInteractor(
        insertRecentCityInteractorImpl: InsertRecentCityInteractorImpl,
    ): InsertRecentCityInteractor

    @Binds
    fun bindDeleteRecentCityInteractor(
        deleteRecentCityInteractorImpl: DeleteRecentCityInteractorImpl,
    ): DeleteRecentCityInteractor

    @Binds
    fun bindGetFavoriteCitiesInteractor(
        getFavoriteCitiesInteractorImpl: GetFavoriteCitiesInteractorImpl,
    ): GetFavoriteCitiesInteractor

    @Binds
    fun bindInsertFavoriteCityInteractor(
        insertFavoriteCityInteractorImpl: InsertFavoriteCityInteractorImpl,
    ): InsertFavoriteCityInteractor

    @Binds
    fun bindDeleteFavoriteCityInteractor(
        deleteFavoriteCityInteractorImpl: DeleteFavoriteCityInteractorImpl,
    ): DeleteFavoriteCityInteractor
}
