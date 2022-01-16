package com.francescsoftware.weathersample.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RepositoryModule_ProvidedOkHttpClientBuilderFactory implements Factory<OkHttpClient.Builder> {
  private final Provider<Cache> cacheProvider;

  private final Provider<Interceptor> headerLoggingInterceptorProvider;

  public RepositoryModule_ProvidedOkHttpClientBuilderFactory(Provider<Cache> cacheProvider,
      Provider<Interceptor> headerLoggingInterceptorProvider) {
    this.cacheProvider = cacheProvider;
    this.headerLoggingInterceptorProvider = headerLoggingInterceptorProvider;
  }

  @Override
  public OkHttpClient.Builder get() {
    return providedOkHttpClientBuilder(cacheProvider.get(), headerLoggingInterceptorProvider.get());
  }

  public static RepositoryModule_ProvidedOkHttpClientBuilderFactory create(
      Provider<Cache> cacheProvider, Provider<Interceptor> headerLoggingInterceptorProvider) {
    return new RepositoryModule_ProvidedOkHttpClientBuilderFactory(cacheProvider, headerLoggingInterceptorProvider);
  }

  public static OkHttpClient.Builder providedOkHttpClientBuilder(Cache cache,
      Interceptor headerLoggingInterceptor) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.providedOkHttpClientBuilder(cache, headerLoggingInterceptor));
  }
}
