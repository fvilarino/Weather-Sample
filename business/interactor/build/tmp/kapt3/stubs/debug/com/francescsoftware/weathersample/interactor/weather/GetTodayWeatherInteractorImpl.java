package com.francescsoftware.weathersample.interactor.weather;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u000e\u0010\u0019\u001a\u00020\u001a*\u0004\u0018\u00010\u0007H\u0002J\u000e\u0010\u001b\u001a\u00020\u001c*\u0004\u0018\u00010\tH\u0002J\u000e\u0010\u001d\u001a\u00020\u001e*\u0004\u0018\u00010\u000fH\u0002J\u000e\u0010\u001f\u001a\u00020 *\u0004\u0018\u00010\rH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\bR\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\t8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\nR\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u000b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\fR\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\r8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u000eR\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u000f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0010R\u001e\u0010\u0005\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u000b0\u00118BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006!"}, d2 = {"Lcom/francescsoftware/weathersample/interactor/weather/GetTodayWeatherInteractorImpl;", "Lcom/francescsoftware/weathersample/interactor/weather/GetTodayWeatherInteractor;", "weatherRepository", "Lcom/francescsoftware/weathersample/repository/weather/WeatherRepository;", "(Lcom/francescsoftware/weathersample/repository/weather/WeatherRepository;)V", "isValid", "", "Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;", "(Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;)Z", "Lcom/francescsoftware/weathersample/repository/weather/model/Main;", "(Lcom/francescsoftware/weathersample/repository/weather/model/Main;)Z", "Lcom/francescsoftware/weathersample/repository/weather/model/WeatherItem;", "(Lcom/francescsoftware/weathersample/repository/weather/model/WeatherItem;)Z", "Lcom/francescsoftware/weathersample/repository/weather/model/Wind;", "(Lcom/francescsoftware/weathersample/repository/weather/model/Wind;)Z", "Lcom/francescsoftware/weathersample/repository/weather/model/today/TodayWeatherResponse;", "(Lcom/francescsoftware/weathersample/repository/weather/model/today/TodayWeatherResponse;)Z", "", "(Ljava/util/List;)Z", "execute", "Lcom/francescsoftware/weathersample/type/Result;", "Lcom/francescsoftware/weathersample/interactor/weather/TodayWeather;", "location", "Lcom/francescsoftware/weathersample/interactor/weather/WeatherLocation;", "(Lcom/francescsoftware/weathersample/interactor/weather/WeatherLocation;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toClouds", "Lcom/francescsoftware/weathersample/interactor/weather/TodayClouds;", "toMain", "Lcom/francescsoftware/weathersample/interactor/weather/TodayMain;", "toWeather", "Lcom/francescsoftware/weathersample/interactor/weather/TodayWeatherItem;", "toWind", "Lcom/francescsoftware/weathersample/interactor/weather/TodayWind;", "interactor_debug"})
public final class GetTodayWeatherInteractorImpl implements com.francescsoftware.weathersample.interactor.weather.GetTodayWeatherInteractor {
    private final com.francescsoftware.weathersample.repository.weather.WeatherRepository weatherRepository = null;
    
    @javax.inject.Inject()
    public GetTodayWeatherInteractorImpl(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.repository.weather.WeatherRepository weatherRepository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object execute(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.interactor.weather.WeatherLocation location, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.francescsoftware.weathersample.type.Result<com.francescsoftware.weathersample.interactor.weather.TodayWeather>> continuation) {
        return null;
    }
    
    private final boolean isValid(com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse $this$isValid) {
        return false;
    }
    
    private final boolean isValid(java.util.List<com.francescsoftware.weathersample.repository.weather.model.WeatherItem> $this$isValid) {
        return false;
    }
    
    private final boolean isValid(com.francescsoftware.weathersample.repository.weather.model.WeatherItem $this$isValid) {
        return false;
    }
    
    private final boolean isValid(com.francescsoftware.weathersample.repository.weather.model.Main $this$isValid) {
        return false;
    }
    
    private final boolean isValid(com.francescsoftware.weathersample.repository.weather.model.Wind $this$isValid) {
        return false;
    }
    
    private final boolean isValid(com.francescsoftware.weathersample.repository.weather.model.Clouds $this$isValid) {
        return false;
    }
    
    private final com.francescsoftware.weathersample.interactor.weather.TodayWeatherItem toWeather(com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse $this$toWeather) {
        return null;
    }
    
    private final com.francescsoftware.weathersample.interactor.weather.TodayMain toMain(com.francescsoftware.weathersample.repository.weather.model.Main $this$toMain) {
        return null;
    }
    
    private final com.francescsoftware.weathersample.interactor.weather.TodayWind toWind(com.francescsoftware.weathersample.repository.weather.model.Wind $this$toWind) {
        return null;
    }
    
    private final com.francescsoftware.weathersample.interactor.weather.TodayClouds toClouds(com.francescsoftware.weathersample.repository.weather.model.Clouds $this$toClouds) {
        return null;
    }
}