package com.francescsoftware.weathersample.interactor.city;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J-\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\f\u0010\u0012\u001a\u00020\f*\u00020\u0007H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2 = {"Lcom/francescsoftware/weathersample/interactor/city/GetCitiesInteractorImpl;", "Lcom/francescsoftware/weathersample/interactor/city/GetCitiesInteractor;", "cityRepository", "Lcom/francescsoftware/weathersample/repository/city/CityRepository;", "(Lcom/francescsoftware/weathersample/repository/city/CityRepository;)V", "isValid", "", "Lcom/francescsoftware/weathersample/repository/city/model/CityItem;", "(Lcom/francescsoftware/weathersample/repository/city/model/CityItem;)Z", "execute", "Lcom/francescsoftware/weathersample/type/Result;", "", "Lcom/francescsoftware/weathersample/interactor/city/City;", "prefix", "", "limit", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toCity", "interactor_debug"})
public final class GetCitiesInteractorImpl implements com.francescsoftware.weathersample.interactor.city.GetCitiesInteractor {
    private final com.francescsoftware.weathersample.repository.city.CityRepository cityRepository = null;
    
    @javax.inject.Inject()
    public GetCitiesInteractorImpl(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.repository.city.CityRepository cityRepository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object execute(@org.jetbrains.annotations.NotNull()
    java.lang.String prefix, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.francescsoftware.weathersample.type.Result<? extends java.util.List<com.francescsoftware.weathersample.interactor.city.City>>> continuation) {
        return null;
    }
    
    private final boolean isValid(com.francescsoftware.weathersample.repository.city.model.CityItem $this$isValid) {
        return false;
    }
    
    private final com.francescsoftware.weathersample.interactor.city.City toCity(com.francescsoftware.weathersample.repository.city.model.CityItem $this$toCity) {
        return null;
    }
}