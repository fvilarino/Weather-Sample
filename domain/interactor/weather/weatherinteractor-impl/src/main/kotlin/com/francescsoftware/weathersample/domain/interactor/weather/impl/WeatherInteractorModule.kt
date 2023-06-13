package com.francescsoftware.weathersample.domain.interactor.weather.impl

import com.francescsoftware.weathersample.domain.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetTodayWeatherInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface WeatherInteractorModule {
    @Binds
    fun bindGetTodayWeatherInteractor(
        getTodayWeatherInteractorImpl: GetTodayWeatherInteractorImpl
    ): GetTodayWeatherInteractor

    @Binds
    fun bindGetForecastInteractor(
        getForecastInteractorImpl: GetForecastInteractorImpl
    ): GetForecastInteractor
}
