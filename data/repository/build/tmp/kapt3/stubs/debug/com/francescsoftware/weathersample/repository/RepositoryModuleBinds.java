package com.francescsoftware.weathersample.repository;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'\u00a8\u0006\u000b"}, d2 = {"Lcom/francescsoftware/weathersample/repository/RepositoryModuleBinds;", "", "()V", "bindCityRepository", "Lcom/francescsoftware/weathersample/repository/city/CityRepository;", "cityRepositoryImpl", "Lcom/francescsoftware/weathersample/repository/city/CityRepositoryImpl;", "bindWeatherRepository", "Lcom/francescsoftware/weathersample/repository/weather/WeatherRepository;", "weatherRepositoryImpl", "Lcom/francescsoftware/weathersample/repository/weather/WeatherRepositoryImpl;", "repository_debug"})
@dagger.Module()
public abstract class RepositoryModuleBinds {
    
    public RepositoryModuleBinds() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Binds()
    public abstract com.francescsoftware.weathersample.repository.city.CityRepository bindCityRepository(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.repository.city.CityRepositoryImpl cityRepositoryImpl);
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Binds()
    public abstract com.francescsoftware.weathersample.repository.weather.WeatherRepository bindWeatherRepository(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.repository.weather.WeatherRepositoryImpl weatherRepositoryImpl);
}