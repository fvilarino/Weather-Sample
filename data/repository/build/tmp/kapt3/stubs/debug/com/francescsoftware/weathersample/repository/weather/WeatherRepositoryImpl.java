package com.francescsoftware.weathersample.repository.weather;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u001f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0007\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u001f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n2\u0006\u0010\u0007\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0010"}, d2 = {"Lcom/francescsoftware/weathersample/repository/weather/WeatherRepositoryImpl;", "Lcom/francescsoftware/weathersample/repository/weather/WeatherRepository;", "weatherService", "Lcom/francescsoftware/weathersample/repository/weather/WeatherService;", "(Lcom/francescsoftware/weathersample/repository/weather/WeatherService;)V", "formatCityQuery", "", "location", "Lcom/francescsoftware/weathersample/repository/weather/WeatherLocation$City;", "getForecast", "Lcom/francescsoftware/weathersample/type/Result;", "Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastResponse;", "Lcom/francescsoftware/weathersample/repository/weather/WeatherLocation;", "(Lcom/francescsoftware/weathersample/repository/weather/WeatherLocation;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTodayWeather", "Lcom/francescsoftware/weathersample/repository/weather/model/today/TodayWeatherResponse;", "repository_debug"})
public final class WeatherRepositoryImpl implements com.francescsoftware.weathersample.repository.weather.WeatherRepository {
    private final com.francescsoftware.weathersample.repository.weather.WeatherService weatherService = null;
    
    @javax.inject.Inject()
    public WeatherRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.repository.weather.WeatherService weatherService) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getTodayWeather(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.repository.weather.WeatherLocation location, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.francescsoftware.weathersample.type.Result<com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getForecast(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.repository.weather.WeatherLocation location, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.francescsoftware.weathersample.type.Result<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse>> continuation) {
        return null;
    }
    
    private final java.lang.String formatCityQuery(com.francescsoftware.weathersample.repository.weather.WeatherLocation.City location) {
        return null;
    }
}