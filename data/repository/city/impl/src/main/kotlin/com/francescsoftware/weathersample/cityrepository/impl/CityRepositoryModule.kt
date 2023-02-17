package com.francescsoftware.weathersample.cityrepository.impl

import com.francescsoftware.weathersample.cityrepository.api.CityRepository
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
internal object CityRepositoryModule {

    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    @CityAuthorizationInterceptor
    fun provideCityAuthorizationInterceptor(): Interceptor = Interceptor { chain ->
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .header(HeaderKey, BuildConfig.RAPID_SERVICE_KEY)
            .header(HeaderHost, BuildConfig.RAPID_SERVICE_CITY_HOST)
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    @CityRetrofit
    fun provideCityRetrofit(
        okHttpClientBuilder: OkHttpClient.Builder,
        @CityAuthorizationInterceptor interceptor: Interceptor,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.CITY_SERVICE_BASE_URL)
        .addConverterFactory(json.asConverterFactory(MediaType))
        .client(okHttpClientBuilder.addInterceptor(interceptor).build())
        .build()

    @Provides
    @Singleton
    fun provideCityService(
        @CityRetrofit retrofit: Retrofit
    ): CityService = retrofit.create(CityService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
internal interface CityRepositoryModuleBinds {
    @Binds
    @Singleton
    fun bindCityRepository(
        cityRepositoryImpl: CityRepositoryImpl
    ): CityRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class CityAuthorizationInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class CityRetrofit
