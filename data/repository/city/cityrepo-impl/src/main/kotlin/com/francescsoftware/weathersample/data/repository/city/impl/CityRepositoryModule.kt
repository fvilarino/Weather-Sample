package com.francescsoftware.weathersample.data.repository.city.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Qualifier

private const val HeaderKey = "x-rapidapi-key"
private const val HeaderHost = "x-rapidapi-host"

@Module
@ContributesTo(AppScope::class)
object CityRepositoryModule {

    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    @SingleIn(AppScope::class)
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
    @SingleIn(AppScope::class)
    @CityRetrofit
    fun provideCityRetrofit(
        okHttpClientBuilder: OkHttpClient.Builder,
        @CityAuthorizationInterceptor interceptor: Interceptor,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.CITY_SERVICE_BASE_URL)
        .addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType(),
            ),
        )
        .client(okHttpClientBuilder.addInterceptor(interceptor).build())
        .build()

    @Provides
    @SingleIn(AppScope::class)
    internal fun provideCityService(
        @CityRetrofit retrofit: Retrofit,
    ): CityService = retrofit.create(CityService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class CityAuthorizationInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class CityRetrofit
