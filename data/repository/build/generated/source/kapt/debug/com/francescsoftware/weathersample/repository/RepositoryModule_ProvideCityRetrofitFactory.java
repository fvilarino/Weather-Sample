package com.francescsoftware.weathersample.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RepositoryModule_ProvideCityRetrofitFactory implements Factory<Retrofit> {
  private final Provider<OkHttpClient.Builder> okHttpClientBuilderProvider;

  private final Provider<Interceptor> interceptorProvider;

  public RepositoryModule_ProvideCityRetrofitFactory(
      Provider<OkHttpClient.Builder> okHttpClientBuilderProvider,
      Provider<Interceptor> interceptorProvider) {
    this.okHttpClientBuilderProvider = okHttpClientBuilderProvider;
    this.interceptorProvider = interceptorProvider;
  }

  @Override
  public Retrofit get() {
    return provideCityRetrofit(okHttpClientBuilderProvider.get(), interceptorProvider.get());
  }

  public static RepositoryModule_ProvideCityRetrofitFactory create(
      Provider<OkHttpClient.Builder> okHttpClientBuilderProvider,
      Provider<Interceptor> interceptorProvider) {
    return new RepositoryModule_ProvideCityRetrofitFactory(okHttpClientBuilderProvider, interceptorProvider);
  }

  public static Retrofit provideCityRetrofit(OkHttpClient.Builder okHttpClientBuilder,
      Interceptor interceptor) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideCityRetrofit(okHttpClientBuilder, interceptor));
  }
}
