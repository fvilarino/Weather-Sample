package com.francescsoftware.weathersample.ui.feature.search.city.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.weather.presenter.WeatherScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

class SearchPresenter @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    private val getCitiesInteractor: GetCitiesInteractor,
    private val insertFavoriteCityInteractor: InsertFavoriteCityInteractor,
    private val deleteFavoriteCityInteractor: DeleteFavoriteCityInteractor,
    private val getFavoriteCitiesInteractor: GetFavoriteCitiesInteractor,
    private val getRecentCitiesInteractor: GetRecentCitiesInteractor,
    private val insertRecentCitiesInteractor: InsertRecentCityInteractor,
    private val deleteRecentCityInteractor: DeleteRecentCityInteractor,
    private val dispatcherProvider: DispatcherProvider,
) : Presenter<SearchScreen.State> {

    @CircuitInject(SearchScreen::class, ActivityScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(
            navigator: Navigator,
        ): SearchPresenter
    }

    @Composable
    override fun present(): SearchScreen.State {
        val scope = rememberCoroutineScope()
        val citiesLoader = rememberRetained {
            CitiesLoader(
                getCitiesInteractor = getCitiesInteractor,
                getFavoriteCitiesInteractor = getFavoriteCitiesInteractor,
                dispatcherProvider = dispatcherProvider,
            )
        }
        val cities by citiesLoader.cities.collectAsRetainedState(initial = SearchScreen.CitiesResult.Idle)
        val recentCities by remember {
            RecentCitiesLoader(
                getRecentCitiesInteractor = getRecentCitiesInteractor,
                dispatcherProvider = dispatcherProvider,
            )
        }.recentCities.collectAsRetainedState(initial = persistentListOf())

        fun eventSink(event: SearchScreen.Event) {
            when (event) {
                is SearchScreen.Event.QueryUpdated -> citiesLoader.setQuery(event.query.text)

                is SearchScreen.Event.ChipClick -> scope.launch {
                    insertRecentCitiesInteractor(RecentCity(name = event.city.name))
                }

                is SearchScreen.Event.DeleteChipClick -> scope.launch {
                    deleteRecentCityInteractor(city = RecentCity(name = event.city.name))
                }

                is SearchScreen.Event.FavoriteClick -> scope.launch {
                    if (event.city.isFavorite) {
                        deleteFavoriteCityInteractor(event.city.toFavoriteCity())
                    } else {
                        insertFavoriteCityInteractor(event.city.toFavoriteCity().copy(id = 0))
                    }
                }

                is SearchScreen.Event.CityClick -> {
                    scope.launch {
                        insertRecentCitiesInteractor(RecentCity(name = event.city.name))
                    }
                    navigator.goTo(WeatherScreen(event.city))
                }
            }
        }

        return SearchScreen.State(
            citiesResult = cities,
            recentCities = recentCities,
            eventSink = ::eventSink,
        )
    }

    private fun CityResultModel.toFavoriteCity(): FavoriteCity = FavoriteCity(
        id = favoriteId,
        name = name,
        countryCode = countryCode,
    )
}
