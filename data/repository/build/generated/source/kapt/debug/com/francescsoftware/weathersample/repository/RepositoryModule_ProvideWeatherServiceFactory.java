package com.francescsoftware.weathersample.repository;

import com.francescsoftware.weathersample.repository.weather.WeatherService;
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
public final class RepositoryModule_ProvideWeatherServiceFactory implements Factory<WeatherService> {
  private final Provider<Retrofit> retrofitProvider;

  public RepositoryModule_ProvideWeatherServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public WeatherService get() {
    return provideWeatherService(retrofitProvider.get());
  }

  public static RepositoryModule_ProvideWeatherServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new RepositoryModule_ProvideWeatherServiceFactory(retrofitProvider);
  }

  public static WeatherService provideWeatherService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideWeatherService(retrofit));
  }
}
