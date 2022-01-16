package com.francescsoftware.weathersample.repository.city;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J/\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0001\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0001\u0010\u0007\u001a\u0004\u0018\u00010\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\n"}, d2 = {"Lcom/francescsoftware/weathersample/repository/city/CityService;", "", "getCities", "Lretrofit2/Response;", "Lcom/francescsoftware/weathersample/repository/city/model/CitySearchResponse;", "namePrefix", "", "limit", "", "(Ljava/lang/String;Ljava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "repository_debug"})
public abstract interface CityService {
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "v1/geo/cities")
    public abstract java.lang.Object getCities(@org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "namePrefix")
    java.lang.String namePrefix, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "limit")
    java.lang.Integer limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.francescsoftware.weathersample.repository.city.model.CitySearchResponse>> continuation);
}