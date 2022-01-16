package com.francescsoftware.weathersample.repository.city;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class CityRepositoryImpl_Factory implements Factory<CityRepositoryImpl> {
  private final Provider<CityService> cityServiceProvider;

  public CityRepositoryImpl_Factory(Provider<CityService> cityServiceProvider) {
    this.cityServiceProvider = cityServiceProvider;
  }

  @Override
  public CityRepositoryImpl get() {
    return newInstance(cityServiceProvider.get());
  }

  public static CityRepositoryImpl_Factory create(Provider<CityService> cityServiceProvider) {
    return new CityRepositoryImpl_Factory(cityServiceProvider);
  }

  public static CityRepositoryImpl newInstance(CityService cityService) {
    return new CityRepositoryImpl(cityService);
  }
}
