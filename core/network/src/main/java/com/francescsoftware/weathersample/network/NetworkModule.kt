package com.francescsoftware.weathersample.network

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

private const val OK_HTTP_TAG = "OkHttp"
private const val CONNECT_TIMEOUT_SECONDS = 15L
private const val READ_TIMEOUT_SECONDS = 30L
private const val CACHE_SIZE = 10L * 1024L * 1024L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @HeaderLoggingInterceptor
    fun provideHeaderLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor { message ->
            Timber.tag(OK_HTTP_TAG).d(message)
        }.apply { level = HttpLoggingInterceptor.Level.HEADERS }


    @Provides
    @Singleton
    fun provideHttpCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, CACHE_SIZE)

    @Provides
    @Singleton
    fun providedOkHttpClientBuilder(
        cache: Cache,
        @HeaderLoggingInterceptor headerLoggingInterceptor: Interceptor,
    ): OkHttpClient.Builder = OkHttpClient
        .Builder()
        .cache(cache)
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(headerLoggingInterceptor)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HeaderLoggingInterceptor
