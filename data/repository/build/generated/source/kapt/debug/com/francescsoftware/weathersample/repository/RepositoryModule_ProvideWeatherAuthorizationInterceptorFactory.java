package com.francescsoftware.weathersample.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import okhttp3.Interceptor;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RepositoryModule_ProvideWeatherAuthorizationInterceptorFactory implements Factory<Interceptor> {
  @Override
  public Interceptor get() {
    return provideWeatherAuthorizationInterceptor();
  }

  public static RepositoryModule_ProvideWeatherAuthorizationInterceptorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static Interceptor provideWeatherAuthorizationInterceptor() {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideWeatherAuthorizationInterceptor());
  }

  private static final class InstanceHolder {
    private static final RepositoryModule_ProvideWeatherAuthorizationInterceptorFactory INSTANCE = new RepositoryModule_ProvideWeatherAuthorizationInterceptorFactory();
  }
}
