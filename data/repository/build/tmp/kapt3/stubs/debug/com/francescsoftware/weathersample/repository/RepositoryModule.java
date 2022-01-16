package com.francescsoftware.weathersample.repository;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\u0004H\u0007J\u0012\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\f\u001a\u00020\u0006H\u0007J\b\u0010\r\u001a\u00020\u0004H\u0007J\u0012\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\u0010\u001a\u00020\u0011H\u0007J\b\u0010\u0012\u001a\u00020\u0004H\u0007J\u001a\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\u0004H\u0007J\u0012\u0010\u0014\u001a\u00020\u00152\b\b\u0001\u0010\f\u001a\u00020\u0006H\u0007J\u001a\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u000f2\b\b\u0001\u0010\u0018\u001a\u00020\u0004H\u0007\u00a8\u0006\u0019"}, d2 = {"Lcom/francescsoftware/weathersample/repository/RepositoryModule;", "", "()V", "provideCityAuthorizationInterceptor", "Lokhttp3/Interceptor;", "provideCityRetrofit", "Lretrofit2/Retrofit;", "okHttpClientBuilder", "Lokhttp3/OkHttpClient$Builder;", "interceptor", "provideCityService", "Lcom/francescsoftware/weathersample/repository/city/CityService;", "retrofit", "provideHeaderLoggingInterceptor", "provideHttpCache", "Lokhttp3/Cache;", "context", "Landroid/content/Context;", "provideWeatherAuthorizationInterceptor", "provideWeatherRetrofit", "provideWeatherService", "Lcom/francescsoftware/weathersample/repository/weather/WeatherService;", "providedOkHttpClientBuilder", "cache", "headerLoggingInterceptor", "repository_debug"})
@dagger.Module()
public final class RepositoryModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.francescsoftware.weathersample.repository.RepositoryModule INSTANCE = null;
    
    private RepositoryModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @HeaderLoggingInterceptor()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final okhttp3.Interceptor provideHeaderLoggingInterceptor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @CityAuthorizationInterceptor()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final okhttp3.Interceptor provideCityAuthorizationInterceptor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @WeatherAuthorizationInterceptor()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final okhttp3.Interceptor provideWeatherAuthorizationInterceptor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final okhttp3.Cache provideHttpCache(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final okhttp3.OkHttpClient.Builder providedOkHttpClientBuilder(@org.jetbrains.annotations.NotNull()
    okhttp3.Cache cache, @org.jetbrains.annotations.NotNull()
    @HeaderLoggingInterceptor()
    okhttp3.Interceptor headerLoggingInterceptor) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @CityRetrofit()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final retrofit2.Retrofit provideCityRetrofit(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient.Builder okHttpClientBuilder, @org.jetbrains.annotations.NotNull()
    @CityAuthorizationInterceptor()
    okhttp3.Interceptor interceptor) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @WeatherRetrofit()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final retrofit2.Retrofit provideWeatherRetrofit(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient.Builder okHttpClientBuilder, @org.jetbrains.annotations.NotNull()
    @WeatherAuthorizationInterceptor()
    okhttp3.Interceptor interceptor) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.francescsoftware.weathersample.repository.city.CityService provideCityService(@org.jetbrains.annotations.NotNull()
    @CityRetrofit()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.francescsoftware.weathersample.repository.weather.WeatherService provideWeatherService(@org.jetbrains.annotations.NotNull()
    @WeatherRetrofit()
    retrofit2.Retrofit retrofit) {
        return null;
    }
}