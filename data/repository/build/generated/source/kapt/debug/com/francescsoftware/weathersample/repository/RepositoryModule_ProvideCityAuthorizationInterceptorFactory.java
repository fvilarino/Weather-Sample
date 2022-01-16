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
public final class RepositoryModule_ProvideCityAuthorizationInterceptorFactory implements Factory<Interceptor> {
  @Override
  public Interceptor get() {
    return provideCityAuthorizationInterceptor();
  }

  public static RepositoryModule_ProvideCityAuthorizationInterceptorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static Interceptor provideCityAuthorizationInterceptor() {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideCityAuthorizationInterceptor());
  }

  private static final class InstanceHolder {
    private static final RepositoryModule_ProvideCityAuthorizationInterceptorFactory INSTANCE = new RepositoryModule_ProvideCityAuthorizationInterceptorFactory();
  }
}
