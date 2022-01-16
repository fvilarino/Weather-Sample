package com.francescsoftware.weathersample.interactor.weather;

import com.francescsoftware.weathersample.repository.weather.WeatherRepository;
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
public final class GetTodayWeatherInteractorImpl_Factory implements Factory<GetTodayWeatherInteractorImpl> {
  private final Provider<WeatherRepository> weatherRepositoryProvider;

  public GetTodayWeatherInteractorImpl_Factory(
      Provider<WeatherRepository> weatherRepositoryProvider) {
    this.weatherRepositoryProvider = weatherRepositoryProvider;
  }

  @Override
  public GetTodayWeatherInteractorImpl get() {
    return newInstance(weatherRepositoryProvider.get());
  }

  public static GetTodayWeatherInteractorImpl_Factory create(
      Provider<WeatherRepository> weatherRepositoryProvider) {
    return new GetTodayWeatherInteractorImpl_Factory(weatherRepositoryProvider);
  }

  public static GetTodayWeatherInteractorImpl newInstance(WeatherRepository weatherRepository) {
    return new GetTodayWeatherInteractorImpl(weatherRepository);
  }
}
