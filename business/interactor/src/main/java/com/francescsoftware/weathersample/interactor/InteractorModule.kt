package com.francescsoftware.weathersample.interactor

import com.francescsoftware.weathersample.interactor.city.GetCitiesInteractor
import com.francescsoftware.weathersample.interactor.city.GetCitiesInteractorImpl
import com.francescsoftware.weathersample.interactor.weather.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.GetTodayWeatherInteractorImpl
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
