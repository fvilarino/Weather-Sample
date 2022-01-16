package com.francescsoftware.weathersample.interactor.weather;

import com.francescsoftware.weathersample.repository.weather.WeatherRepository;
import com.francescsoftware.weathersample.utils.dispatcher.DispatcherProvider;
import com.francescsoftware.weathersample.utils.time.TimeFormatter;
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
public final class GetForecastInteractorImpl_Factory implements Factory<GetForecastInteractorImpl> {
  private final Provider<WeatherRepository> weatherRepositoryProvider;

  private final Provider<DispatcherProvider> dispatcherProvider;

  private final Provider<TimeFormatter> timeFormatterProvider;

  public GetForecastInteractorImpl_Factory(Provider<WeatherRepository> weatherRepositoryProvider,
      Provider<DispatcherProvider> dispatcherProvider,
      Provider<TimeFormatter> timeFormatterProvider) {
    this.weatherRepositoryProvider = weatherRepositoryProvider;
    this.dispatcherProvider = dispatcherProvider;
    this.timeFormatterProvider = timeFormatterProvider;
  }

  @Override
  public GetForecastInteractorImpl get() {
    return newInstance(weatherRepositoryProvider.get(), dispatcherProvider.get(), timeFormatterProvider.get());
  }

  public static GetForecastInteractorImpl_Factory create(
      Provider<WeatherRepository> weatherRepositoryProvider,
      Provider<DispatcherProvider> dispatcherProvider,
      Provider<TimeFormatter> timeFormatterProvider) {
    return new GetForecastInteractorImpl_Factory(weatherRepositoryProvider, dispatcherProvider, timeFormatterProvider);
  }

  public static GetForecastInteractorImpl newInstance(WeatherRepository weatherRepository,
      DispatcherProvider dispatcherProvider, TimeFormatter timeFormatter) {
    return new GetForecastInteractorImpl(weatherRepository, dispatcherProvider, timeFormatter);
  }
}
