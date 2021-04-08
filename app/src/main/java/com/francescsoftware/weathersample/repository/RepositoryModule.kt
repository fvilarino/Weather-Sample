package com.francescsoftware.weathersample.repository

import android.content.Context
import com.francescsoftware.weathersample.BuildConfig
import com.francescsoftware.weathersample.repository.city.CityRepository
import com.francescsoftware.weathersample.repository.city.CityRepositoryImpl
import com.francescsoftware.weathersample.repository.city.CityService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

private val MEDIA_TYPE = "application/json".toMediaType()
private const val CONNECT_TIMEOUT_SECONDS = 15L
private const val READ_TIMEOUT_SECONDS = 30L
private const val CACHE_SIZE = 10L * 1024L * 1024L
private const val HEADER_KEY = "x-rapidapi-key"
private const val HEADER_HOST = "x-rapidapi-host"

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    @HeaderLoggingInterceptor
    fun provideHeaderLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }

    @Provides
    @Singleton
    @AuthorizationInterceptor
    fun provideAuthorizationInterceptor(): Interceptor = Interceptor { chain ->
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .header(HEADER_KEY, BuildConfig.RAPID_SERVICE_KEY)
            .header(HEADER_HOST, BuildConfig.RAPID_SERVICE_HOST)
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideHttpCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, CACHE_SIZE)

    @Provides
    @Singleton
    fun providedOkHttpClient(
        cache: Cache,
        @HeaderLoggingInterceptor headerLoggingInterceptor: Interceptor,
        @AuthorizationInterceptor authorizationInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .cache(cache)
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(headerLoggingInterceptor)
        .addInterceptor(authorizationInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.CITY_SERVICE_BASE_URL)
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(MEDIA_TYPE))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideCityService(
        retrofit: Retrofit
    ): CityService = retrofit.create(CityService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinds {
    @Binds
    @Singleton
    abstract fun bindCityRepository(cityRepositoryImpl: CityRepositoryImpl): CityRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HeaderLoggingInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthorizationInterceptor
