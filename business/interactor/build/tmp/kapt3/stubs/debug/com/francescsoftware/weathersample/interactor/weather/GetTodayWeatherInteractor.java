package com.francescsoftware.weathersample.interactor.weather;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\b"}, d2 = {"Lcom/francescsoftware/weathersample/interactor/weather/GetTodayWeatherInteractor;", "", "execute", "Lcom/francescsoftware/weathersample/type/Result;", "Lcom/francescsoftware/weathersample/interactor/weather/TodayWeather;", "location", "Lcom/francescsoftware/weathersample/interactor/weather/WeatherLocation;", "(Lcom/francescsoftware/weathersample/interactor/weather/WeatherLocation;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "interactor_debug"})
public abstract interface GetTodayWeatherInteractor {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object execute(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.interactor.weather.WeatherLocation location, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.francescsoftware.weathersample.type.Result<com.francescsoftware.weathersample.interactor.weather.TodayWeather>> continuation);
}