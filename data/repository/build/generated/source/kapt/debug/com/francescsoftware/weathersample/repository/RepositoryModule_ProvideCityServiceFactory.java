package com.francescsoftware.weathersample.repository;

import com.francescsoftware.weathersample.repository.city.CityService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
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
public final class RepositoryModule_ProvideCityServiceFactory implements Factory<CityService> {
  private final Provider<Retrofit> retrofitProvider;

  public RepositoryModule_ProvideCityServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public CityService get() {
    return provideCityService(retrofitProvider.get());
  }

  public static RepositoryModule_ProvideCityServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new RepositoryModule_ProvideCityServiceFactory(retrofitProvider);
  }

  public static CityService provideCityService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideCityService(retrofit));
  }
}
