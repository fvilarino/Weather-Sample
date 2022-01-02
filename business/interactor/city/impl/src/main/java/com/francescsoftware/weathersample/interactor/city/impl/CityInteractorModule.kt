package com.francescsoftware.weathersample.interactor.city.impl

import com.francescsoftware.weathersample.interactor.city.api.GetCitiesInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CityInteractorModule {
    @Binds
    internal abstract fun bindGetCitiesInteractor(
        getCitiesInteractorImpl: GetCitiesInteractorImpl
    ): GetCitiesInteractor
}
