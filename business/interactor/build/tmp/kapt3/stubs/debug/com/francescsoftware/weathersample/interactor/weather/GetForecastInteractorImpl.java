package com.francescsoftware.weathersample.interactor.weather;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J\'\u0010\u0015\u001a\u00020\u00112\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\u00172\u0006\u0010\u0018\u001a\u00020\u000bH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\f\u0010\u001a\u001a\u00020\u001b*\u00020\rH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\fR\u0018\u0010\t\u001a\u00020\n*\u00020\r8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/francescsoftware/weathersample/interactor/weather/GetForecastInteractorImpl;", "Lcom/francescsoftware/weathersample/interactor/weather/GetForecastInteractor;", "weatherRepository", "Lcom/francescsoftware/weathersample/repository/weather/WeatherRepository;", "dispatcherProvider", "Lcom/francescsoftware/weathersample/utils/dispatcher/DispatcherProvider;", "timeFormatter", "Lcom/francescsoftware/weathersample/utils/time/TimeFormatter;", "(Lcom/francescsoftware/weathersample/repository/weather/WeatherRepository;Lcom/francescsoftware/weathersample/utils/dispatcher/DispatcherProvider;Lcom/francescsoftware/weathersample/utils/time/TimeFormatter;)V", "isValid", "", "Lcom/francescsoftware/weathersample/repository/weather/model/forecast/City;", "(Lcom/francescsoftware/weathersample/repository/weather/model/forecast/City;)Z", "Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastItem;", "(Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastItem;)Z", "execute", "Lcom/francescsoftware/weathersample/type/Result;", "Lcom/francescsoftware/weathersample/interactor/weather/Forecast;", "location", "Lcom/francescsoftware/weathersample/interactor/weather/WeatherLocation;", "(Lcom/francescsoftware/weathersample/interactor/weather/WeatherLocation;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseForecast", "forecast", "", "city", "(Ljava/util/List;Lcom/francescsoftware/weathersample/repository/weather/model/forecast/City;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toForecastEntry", "Lcom/francescsoftware/weathersample/interactor/weather/ForecastEntry;", "interactor_debug"})
public final class GetForecastInteractorImpl implements com.francescsoftware.weathersample.interactor.weather.GetForecastInteractor {
    private final com.francescsoftware.weathersample.repository.weather.WeatherRepository weatherRepository = null;
    private final com.francescsoftware.weathersample.utils.dispatcher.DispatcherProvider dispatcherProvider = null;
    private final com.francescsoftware.weathersample.utils.time.TimeFormatter timeFormatter = null;
    
    @javax.inject.Inject()
    public GetForecastInteractorImpl(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.repository.weather.WeatherRepository weatherRepository, @org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.utils.dispatcher.DispatcherProvider dispatcherProvider, @org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.utils.time.TimeFormatter timeFormatter) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object execute(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.interactor.weather.WeatherLocation location, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.francescsoftware.weathersample.type.Result<com.francescsoftware.weathersample.interactor.weather.Forecast>> continuation) {
        return null;
    }
    
    private final java.lang.Object parseForecast(java.util.List<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem> forecast, com.francescsoftware.weathersample.repository.weather.model.forecast.City city, kotlin.coroutines.Continuation<? super com.francescsoftware.weathersample.interactor.weather.Forecast> continuation) {
        return null;
    }
    
    private final com.francescsoftware.weathersample.interactor.weather.ForecastEntry toForecastEntry(com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem $this$toForecastEntry) {
        return null;
    }
    
    private final boolean isValid(com.francescsoftware.weathersample.repository.weather.model.forecast.City $this$isValid) {
        return false;
    }
    
    private final boolean isValid(com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem $this$isValid) {
        return false;
    }
}