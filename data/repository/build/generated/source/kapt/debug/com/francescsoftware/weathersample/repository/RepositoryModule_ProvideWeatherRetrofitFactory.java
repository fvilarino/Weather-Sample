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
public final class RepositoryModule_ProvideWeatherRetrofitFactory implements Factory<Retrofit> {
  private final Provider<OkHttpClient.Builder> okHttpClientBuilderProvider;

  private final Provider<Interceptor> interceptorProvider;

  public RepositoryModule_ProvideWeatherRetrofitFactory(
      Provider<OkHttpClient.Builder> okHttpClientBuilderProvider,
      Provider<Interceptor> interceptorProvider) {
    this.okHttpClientBuilderProvider = okHttpClientBuilderProvider;
    this.interceptorProvider = interceptorProvider;
  }

  @Override
  public Retrofit get() {
    return provideWeatherRetrofit(okHttpClientBuilderProvider.get(), interceptorProvider.get());
  }

  public static RepositoryModule_ProvideWeatherRetrofitFactory create(
      Provider<OkHttpClient.Builder> okHttpClientBuilderProvider,
      Provider<Interceptor> interceptorProvider) {
    return new RepositoryModule_ProvideWeatherRetrofitFactory(okHttpClientBuilderProvider, interceptorProvider);
  }

  public static Retrofit provideWeatherRetrofit(OkHttpClient.Builder okHttpClientBuilder,
      Interceptor interceptor) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideWeatherRetrofit(okHttpClientBuilder, interceptor));
  }
}
