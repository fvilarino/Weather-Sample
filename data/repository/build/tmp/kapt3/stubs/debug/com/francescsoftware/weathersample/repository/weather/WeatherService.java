package com.francescsoftware.weathersample.repository.weather;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001JE\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0003\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0003\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0003\u0010\t\u001a\u0004\u0018\u00010\b2\b\b\u0003\u0010\n\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJE\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\n\b\u0003\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0003\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0003\u0010\t\u001a\u0004\u0018\u00010\b2\b\b\u0003\u0010\n\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2 = {"Lcom/francescsoftware/weathersample/repository/weather/WeatherService;", "", "getForecast", "Lretrofit2/Response;", "Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastResponse;", "cityAndCountry", "", "latitude", "", "longitude", "units", "(Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTodayWeather", "Lcom/francescsoftware/weathersample/repository/weather/model/today/TodayWeatherResponse;", "repository_debug"})
public abstract interface WeatherService {
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "weather")
    public abstract java.lang.Object getTodayWeather(@org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "q")
    java.lang.String cityAndCountry, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "lat")
    java.lang.Double latitude, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "lon")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Query(value = "units")
    java.lang.String units, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "forecast")
    public abstract java.lang.Object getForecast(@org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "q")
    java.lang.String cityAndCountry, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "lat")
    java.lang.Double latitude, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "lon")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Query(value = "units")
    java.lang.String units, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse>> continuation);
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 3)
    public final class DefaultImpls {
    }
}