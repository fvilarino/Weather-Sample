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

private const val OkHttpTag = "OkHttp"
private const val ConnectTimeoutSeconds = 15L
private const val ReadTimeoutSeconds = 30L
private const val CacheSize = 10L * 1024L * 1024L

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    @HeaderLoggingInterceptor
    fun provideHeaderLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor { message ->
            Timber.tag(OkHttpTag).d(message)
        }.apply { level = HttpLoggingInterceptor.Level.HEADERS }

    @Provides
    @Singleton
    fun provideHttpCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, CacheSize)

    @Provides
    @Singleton
    fun providedOkHttpClientBuilder(
        cache: Cache,
        @HeaderLoggingInterceptor headerLoggingInterceptor: Interceptor,
    ): OkHttpClient.Builder = OkHttpClient
        .Builder()
        .cache(cache)
        .connectTimeout(ConnectTimeoutSeconds, TimeUnit.SECONDS)
        .readTimeout(ReadTimeoutSeconds, TimeUnit.SECONDS)
        .addInterceptor(headerLoggingInterceptor)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class HeaderLoggingInterceptor
