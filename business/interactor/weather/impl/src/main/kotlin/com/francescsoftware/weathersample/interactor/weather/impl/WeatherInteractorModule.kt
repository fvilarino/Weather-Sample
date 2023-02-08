package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.interactor.weather.api.GetTodayWeatherInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class WeatherInteractorModule {
    @Binds
    internal abstract fun bindGetTodayWeatherInteractor(
        getTodayWeatherInteractorImpl: GetTodayWeatherInteractorImpl
    ): GetTodayWeatherInteractor

    @Binds
    internal abstract fun bindGetForecastInteractor(
        getForecastInteractorImpl: GetForecastInteractorImpl
    ): GetForecastInteractor
}
