package com.francescsoftware.weathersample.interactor;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.android.components.ViewModelComponent.class})
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\'\u00a8\u0006\u000f"}, d2 = {"Lcom/francescsoftware/weathersample/interactor/InteractorModule;", "", "()V", "bindGetCitiesInteractor", "Lcom/francescsoftware/weathersample/interactor/city/GetCitiesInteractor;", "getCitiesInteractorImpl", "Lcom/francescsoftware/weathersample/interactor/city/GetCitiesInteractorImpl;", "bindGetForecastInteractor", "Lcom/francescsoftware/weathersample/interactor/weather/GetForecastInteractor;", "getForecastInteractorImpl", "Lcom/francescsoftware/weathersample/interactor/weather/GetForecastInteractorImpl;", "bindGetTodayWeatherInteractor", "Lcom/francescsoftware/weathersample/interactor/weather/GetTodayWeatherInteractor;", "getTodayWeatherInteractorImpl", "Lcom/francescsoftware/weathersample/interactor/weather/GetTodayWeatherInteractorImpl;", "interactor_debug"})
@dagger.Module()
public abstract class InteractorModule {
    
    public InteractorModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract com.francescsoftware.weathersample.interactor.city.GetCitiesInteractor bindGetCitiesInteractor(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.interactor.city.GetCitiesInteractorImpl getCitiesInteractorImpl);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract com.francescsoftware.weathersample.interactor.weather.GetTodayWeatherInteractor bindGetTodayWeatherInteractor(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.interactor.weather.GetTodayWeatherInteractorImpl getTodayWeatherInteractorImpl);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract com.francescsoftware.weathersample.interactor.weather.GetForecastInteractor bindGetForecastInteractor(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.interactor.weather.GetForecastInteractorImpl getForecastInteractorImpl);
}