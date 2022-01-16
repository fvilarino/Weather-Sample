package com.francescsoftware.weathersample.repository.weather;

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
public final class WeatherRepositoryImpl_Factory implements Factory<WeatherRepositoryImpl> {
  private final Provider<WeatherService> weatherServiceProvider;

  public WeatherRepositoryImpl_Factory(Provider<WeatherService> weatherServiceProvider) {
    this.weatherServiceProvider = weatherServiceProvider;
  }

  @Override
  public WeatherRepositoryImpl get() {
    return newInstance(weatherServiceProvider.get());
  }

  public static WeatherRepositoryImpl_Factory create(
      Provider<WeatherService> weatherServiceProvider) {
    return new WeatherRepositoryImpl_Factory(weatherServiceProvider);
  }

  public static WeatherRepositoryImpl newInstance(WeatherService weatherService) {
    return new WeatherRepositoryImpl(weatherService);
  }
}
