package com.francescsoftware.weathersample.repository;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.Cache;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RepositoryModule_ProvideHttpCacheFactory implements Factory<Cache> {
  private final Provider<Context> contextProvider;

  public RepositoryModule_ProvideHttpCacheFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public Cache get() {
    return provideHttpCache(contextProvider.get());
  }

  public static RepositoryModule_ProvideHttpCacheFactory create(Provider<Context> contextProvider) {
    return new RepositoryModule_ProvideHttpCacheFactory(contextProvider);
  }

  public static Cache provideHttpCache(Context context) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideHttpCache(context));
  }
}
