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
public final class RepositoryModule_ProvideHeaderLoggingInterceptorFactory implements Factory<Interceptor> {
  @Override
  public Interceptor get() {
    return provideHeaderLoggingInterceptor();
  }

  public static RepositoryModule_ProvideHeaderLoggingInterceptorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static Interceptor provideHeaderLoggingInterceptor() {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideHeaderLoggingInterceptor());
  }

  private static final class InstanceHolder {
    private static final RepositoryModule_ProvideHeaderLoggingInterceptorFactory INSTANCE = new RepositoryModule_ProvideHeaderLoggingInterceptorFactory();
  }
}
