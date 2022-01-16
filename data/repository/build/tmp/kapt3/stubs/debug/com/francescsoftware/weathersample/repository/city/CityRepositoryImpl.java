package com.francescsoftware.weathersample.repository.city;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\'\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\r"}, d2 = {"Lcom/francescsoftware/weathersample/repository/city/CityRepositoryImpl;", "Lcom/francescsoftware/weathersample/repository/city/CityRepository;", "cityService", "Lcom/francescsoftware/weathersample/repository/city/CityService;", "(Lcom/francescsoftware/weathersample/repository/city/CityService;)V", "getCities", "Lcom/francescsoftware/weathersample/type/Result;", "Lcom/francescsoftware/weathersample/repository/city/model/CitySearchResponse;", "prefix", "", "limit", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "repository_debug"})
public final class CityRepositoryImpl implements com.francescsoftware.weathersample.repository.city.CityRepository {
    private final com.francescsoftware.weathersample.repository.city.CityService cityService = null;
    
    @javax.inject.Inject()
    public CityRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.repository.city.CityService cityService) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getCities(@org.jetbrains.annotations.NotNull()
    java.lang.String prefix, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.francescsoftware.weathersample.type.Result<com.francescsoftware.weathersample.repository.city.model.CitySearchResponse>> continuation) {
        return null;
    }
}