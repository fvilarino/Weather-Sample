package com.francescsoftware.weathersample.business.interactor

import com.francescsoftware.weathersample.business.interactor.city.GetCitiesInteractor
import com.francescsoftware.weathersample.business.interactor.city.GetCitiesInteractorImpl
import com.francescsoftware.weathersample.business.interactor.weather.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.business.interactor.weather.GetTodayWeatherInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class InteractorModule {
    @Binds
    abstract fun bindGetCitiesInteractor(
        getCitiesInteractorImpl: GetCitiesInteractorImpl
    ): GetCitiesInteractor

    @Binds
    abstract fun bindGetTodayWeatherInteractor(
        getTodayWeatherInteractorImpl: GetTodayWeatherInteractorImpl
    ): GetTodayWeatherInteractor
}
