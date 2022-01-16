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

private val MEDIA_TYPE = "application/json".toMediaType()
private const val HEADER_KEY = "x-rapidapi-key"
private const val HEADER_HOST = "x-rapidapi-host"

@Module
@InstallIn(SingletonComponent::class)
object CityRepositoryModule {

    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    @CityAuthorizationInterceptor
    internal fun provideCityAuthorizationInterceptor(): Interceptor = Interceptor { chain ->
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .header(HEADER_KEY, BuildConfig.RAPID_SERVICE_KEY)
            .header(HEADER_HOST, BuildConfig.RAPID_SERVICE_CITY_HOST)
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    @CityRetrofit
    internal fun provideCityRetrofit(
        okHttpClientBuilder: OkHttpClient.Builder,
        @CityAuthorizationInterceptor interceptor: Interceptor,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.CITY_SERVICE_BASE_URL)
        .addConverterFactory(json.asConverterFactory(MEDIA_TYPE))
        .client(okHttpClientBuilder.addInterceptor(interceptor).build())
        .build()

    @Provides
    @Singleton
    internal fun provideCityService(
        @CityRetrofit retrofit: Retrofit
    ): CityService = retrofit.create(CityService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CityRepositoryModuleBinds {
    @Binds
    @Singleton
    internal abstract fun bindCityRepository(
        cityRepositoryImpl: CityRepositoryImpl
    ): CityRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CityAuthorizationInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CityRetrofit
