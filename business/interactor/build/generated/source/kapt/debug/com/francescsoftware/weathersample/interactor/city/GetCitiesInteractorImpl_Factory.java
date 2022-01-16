package com.francescsoftware.weathersample.interactor.city;

import com.francescsoftware.weathersample.repository.city.CityRepository;
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
public final class GetCitiesInteractorImpl_Factory implements Factory<GetCitiesInteractorImpl> {
  private final Provider<CityRepository> cityRepositoryProvider;

  public GetCitiesInteractorImpl_Factory(Provider<CityRepository> cityRepositoryProvider) {
    this.cityRepositoryProvider = cityRepositoryProvider;
  }

  @Override
  public GetCitiesInteractorImpl get() {
    return newInstance(cityRepositoryProvider.get());
  }

  public static GetCitiesInteractorImpl_Factory create(
      Provider<CityRepository> cityRepositoryProvider) {
    return new GetCitiesInteractorImpl_Factory(cityRepositoryProvider);
  }

  public static GetCitiesInteractorImpl newInstance(CityRepository cityRepository) {
    return new GetCitiesInteractorImpl(cityRepository);
  }
}
