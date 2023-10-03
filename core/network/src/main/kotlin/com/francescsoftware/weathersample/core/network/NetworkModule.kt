package com.francescsoftware.weathersample.core.network

import android.content.Context
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.ApplicationContext
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

private const val OkHttpTag = "OkHttp"
private const val ConnectTimeoutSeconds = 15L
private const val ReadTimeoutSeconds = 30L
private const val CacheSize = 10L * 1024L * 1024L

@Module
@ContributesTo(AppScope::class)
internal object NetworkModule {

    @Provides
    @SingleIn(AppScope::class)
    @HeaderLoggingInterceptor
    fun provideHeaderLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor { message ->
            Timber.tag(OkHttpTag).d(message)
        }.apply { level = HttpLoggingInterceptor.Level.HEADERS }

    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, CacheSize)

    @Provides
    @SingleIn(AppScope::class)
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
