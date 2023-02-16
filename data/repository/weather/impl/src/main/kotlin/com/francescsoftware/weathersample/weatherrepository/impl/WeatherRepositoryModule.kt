package com.francescsoftware.weathersample.weatherrepository.impl

import com.francescsoftware.weathersample.weatherrepository.api.WeatherRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

private val MediaType = "application/json".toMediaType()
private const val HeaderKey = "x-rapidapi-key"
private const val HeaderHost = "x-rapidapi-host"

@Module
@InstallIn(SingletonComponent::class)
object WeatherRepositoryModule {

    @Provides
    @Singleton
    @WeatherAuthorizationInterceptor
    internal fun provideWeatherAuthorizationInterceptor(): Interceptor = Interceptor { chain ->
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .header(HeaderKey, BuildConfig.RAPID_SERVICE_KEY)
            .header(HeaderHost, BuildConfig.RAPID_SERVICE_WEATHER_HOST)
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    @WeatherRetrofit
    internal fun provideWeatherRetrofit(
        okHttpClientBuilder: OkHttpClient.Builder,
        @WeatherAuthorizationInterceptor interceptor: Interceptor,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.WEATHER_SERVICE_BASE_URL)
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(MediaType))
        .client(okHttpClientBuilder.addInterceptor(interceptor).build())
        .build()

    @Provides
    @Singleton
    internal fun provideWeatherService(
        @WeatherRetrofit retrofit: Retrofit
    ): WeatherService = retrofit.create(WeatherService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherRepositoryModuleBinds {

    @Binds
    @Singleton
    internal abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class WeatherAuthorizationInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class WeatherRetrofit
