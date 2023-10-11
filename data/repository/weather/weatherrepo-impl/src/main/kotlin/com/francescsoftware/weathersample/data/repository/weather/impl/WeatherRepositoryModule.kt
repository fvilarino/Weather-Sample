package com.francescsoftware.weathersample.data.repository.weather.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import javax.inject.Qualifier

private val MediaType = "application/json".toMediaType()
private val json = Json { ignoreUnknownKeys = true }
private const val HeaderKey = "x-rapidapi-key"
private const val HeaderHost = "x-rapidapi-host"

@Module
@ContributesTo(AppScope::class)
object WeatherRepositoryModule {

    @Provides
    @SingleIn(AppScope::class)
    @WeatherAuthorizationInterceptor
    fun provideWeatherAuthorizationInterceptor(): Interceptor = Interceptor { chain ->
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .header(HeaderKey, BuildConfig.RAPID_SERVICE_KEY)
            .header(HeaderHost, BuildConfig.RAPID_SERVICE_WEATHER_HOST)
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    @Provides
    @SingleIn(AppScope::class)
    @WeatherRetrofit
    fun provideWeatherRetrofit(
        okHttpClientBuilder: OkHttpClient.Builder,
        @WeatherAuthorizationInterceptor interceptor: Interceptor,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.WEATHER_SERVICE_BASE_URL)
        .addConverterFactory(json.asConverterFactory(MediaType))
        .client(okHttpClientBuilder.addInterceptor(interceptor).build())
        .build()

    @Provides
    @SingleIn(AppScope::class)
    internal fun provideWeatherService(
        @WeatherRetrofit retrofit: Retrofit,
    ): WeatherService = retrofit.create(WeatherService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class WeatherAuthorizationInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class WeatherRetrofit
