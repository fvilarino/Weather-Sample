package com.francescsoftware.weathersample.ui.feature.search.city.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.ReceiveTurbine
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Coordinates
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.testing.fake.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.city.presenter.SearchPresenter
import com.francescsoftware.weathersample.ui.feature.search.city.presenter.SearchScreen
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

private const val VancouverCityId = 1
private const val VancouverCityName = "Vancouver"
private const val VancouverCityRegion = "British Columbia"
private const val VancouverCityRegionCode = "BC"
private const val VancouverCityCountry = "Canada"
private const val VancouverCityCountryCode = "CA"
private const val VancouverCityLatitude = 49.24
private const val VancouverCityLongitude = -123.11

private const val BarcelonaCityId = 2
private const val BarcelonaCityName = "Barcelona"
private const val BarcelonaCityRegion = "Catalunya"
private const val BarcelonaCityRegionCode = "CA"
private const val BarcelonaCityCountry = "Spain"
private const val BarcelonaCityCountryCode = "ES"
private const val BarcelonaCityLatitude = 41.39
private const val BarcelonaCityLongitude = 2.17

private const val FavoriteCityVancouverId = 1
private const val FavoriteCityVancouverName = "Vancouver"
private const val FavoriteCityVancouverCode = "CA"

private const val RecentCityVancouver = "Vancouver"
private const val RecentCityBarcelona = "Barcelona"

internal class SearchPresenterTest {

    private val DomainCityVancouver = City(
        id = VancouverCityId,
        name = VancouverCityName,
        region = VancouverCityRegion,
        regionCode = VancouverCityRegionCode,
        country = VancouverCityCountry,
        countryCode = VancouverCityCountryCode,
        coordinates = Coordinates(
            latitude = VancouverCityLatitude,
            longitude = VancouverCityLongitude,
        ),
    )

    private val DomainCityBarcelona = City(
        id = BarcelonaCityId,
        name = BarcelonaCityName,
        region = BarcelonaCityRegion,
        regionCode = BarcelonaCityRegionCode,
        country = BarcelonaCityCountry,
        countryCode = BarcelonaCityCountryCode,
        coordinates = Coordinates(
            latitude = BarcelonaCityLatitude,
            longitude = BarcelonaCityLongitude,
        ),
    )

    private val CityModelVancouver = CityResultModel(
        id = VancouverCityId.toLong(),
        favoriteId = -1,
        name = VancouverCityName,
        country = VancouverCityCountry,
        countryCode = VancouverCityCountryCode,
        coordinates = com.francescsoftware.weathersample.ui.feature.search.city.model.Coordinates(
            latitude = VancouverCityLatitude.toFloat(),
            longitude = VancouverCityLongitude.toFloat(),
        ),
    )

    private val CityModelBarcelona = CityResultModel(
        id = BarcelonaCityId.toLong(),
        favoriteId = -1,
        name = BarcelonaCityName,
        country = BarcelonaCityCountry,
        countryCode = BarcelonaCityCountryCode,
        coordinates = com.francescsoftware.weathersample.ui.feature.search.city.model.Coordinates(
            latitude = BarcelonaCityLatitude.toFloat(),
            longitude = BarcelonaCityLongitude.toFloat(),
        ),
    )

    private val FavoriteCityVancouver = FavoriteCity(
        id = FavoriteCityVancouverId,
        name = FavoriteCityVancouverName,
        countryCode = FavoriteCityVancouverCode,
    )

    private val RecentCities = listOf(
        RecentCity(
            RecentCityVancouver,
        ),
        RecentCity(
            RecentCityBarcelona,
        ),
    )

    @Test
    @DisplayName("Presenter loads cities from query")
    fun presenterLoadsCitiesFromQuery() = runTest {
        val citiesInteractor = FakeGetCitiesInteractor().apply {
            citiesResult.add(listOf(DomainCityVancouver, DomainCityBarcelona))
        }
        val presenter = getPresenter(
            getCitiesInteractor = citiesInteractor,
        )
        val query = "query"
        presenter.test {
            var item = awaitItem()
            assertThat(item.citiesResult).isInstanceOf<SearchScreen.CitiesResult.Idle>()
            item.eventSink(SearchScreen.Event.QueryUpdated(TextFieldValue(query)))
            item = awaitItem()
            assertThat(item.citiesResult).isInstanceOf<SearchScreen.CitiesResult.Loading>()
            item = awaitItem()
            assertThat(item.citiesResult).isInstanceOf<SearchScreen.CitiesResult.Loaded>()
            assertThat((item.citiesResult as SearchScreen.CitiesResult.Loaded).cities).isEqualTo(
                persistentListOf(CityModelVancouver, CityModelBarcelona),
            )
        }
    }

    @Test
    @DisplayName("Presenter indicates error when failing to load cities")
    fun presenterShowErrorWhenCitiesLoadFails() = runTest {
        val citiesInteractor = FakeGetCitiesInteractor().apply {
            citiesResult.add(null)
        }
        val presenter = getPresenter(
            getCitiesInteractor = citiesInteractor,
        )
        val query = "query"
        presenter.test {
            var item = awaitItem()
            assertThat(item.citiesResult).isInstanceOf<SearchScreen.CitiesResult.Idle>()
            item.eventSink(SearchScreen.Event.QueryUpdated(TextFieldValue(query)))
            item = awaitItem()
            assertThat(item.citiesResult).isInstanceOf<SearchScreen.CitiesResult.Loading>()
            item = awaitItem()
            assertThat(item.citiesResult).isInstanceOf<SearchScreen.CitiesResult.Error>()
        }
    }

    @Test
    @DisplayName("Presenter loads recent cities")
    fun presenterLoadsRecentCities() = runTest {
        val recentCitiesInteractor = FakeGetRecentCitiesInteractor().apply {
            cities.add(RecentCities)
        }
        val presenter = getPresenter(
            getRecentCitiesInteractor = recentCitiesInteractor,
        )
        presenter.test {
            var item = awaitItem()
            assertThat(item.recentCities).isEmpty()
            item = awaitItem()
            val expected = RecentCities
                .map { city -> RecentCityModel(city.name) }
                .sortedBy { city -> city.name }
                .toPersistentList()
            assertThat(item.recentCities).isEqualTo(expected)
        }
    }

    @Test
    @DisplayName("Presenter saves recent city chip click")
    fun presenterLoadsCitiesOnChipClick() = runTest {
        val recentCitiesInteractor = FakeGetRecentCitiesInteractor().apply {
            cities.add(RecentCities)
        }
        val insertRecentCityInteractor = FakeInsertRecentCityInterator()
        val presenter = getPresenter(
            getRecentCitiesInteractor = recentCitiesInteractor,
            insertRecentCitiesInteractor = insertRecentCityInteractor,
        )
        presenter.test {
            val item = skipWhile { state ->
                state.recentCities.isEmpty()
            }
            val recentCity = RecentCityModel(RecentCityVancouver)
            item.eventSink(SearchScreen.Event.ChipClick(recentCity))
            val city = insertRecentCityInteractor.cities.awaitItem()
            assertThat(city).isEqualTo(RecentCity(name = recentCity.name))
        }
    }

    @Test
    @DisplayName("Presenter removes recent city from database on X click")
    fun presenterRemovesRecentCityOnXClick() = runTest {
        val recentCitiesInteractor = FakeGetRecentCitiesInteractor().apply {
            cities.add(RecentCities)
        }
        val deleteRecentCityInteractor = FakeDeleteRecentCityInteractor()
        val presenter = getPresenter(
            getRecentCitiesInteractor = recentCitiesInteractor,
            deleteRecentCityInteractor = deleteRecentCityInteractor,
        )
        presenter.test {
            val item = skipWhile { state ->
                state.recentCities.isEmpty()
            }
            val recentCity = RecentCityModel(RecentCityVancouver)
            item.eventSink(SearchScreen.Event.DeleteChipClick(recentCity))
            val city = deleteRecentCityInteractor.cities.awaitItem()
            assertThat(city).isEqualTo(RecentCity(name = recentCity.name))
        }
    }

    @Test
    @DisplayName("Presenter inserts favorite city on favorite click")
    fun presenterInsertsFavoriteCityOnClick() = runTest {
        val citiesInteractor = FakeGetCitiesInteractor().apply {
            citiesResult.add(listOf(DomainCityVancouver, DomainCityBarcelona))
        }
        val insertFavoriteCitiesInteractor = FakeInsertFavoriteCityInteractor()
        val presenter = getPresenter(
            getCitiesInteractor = citiesInteractor,
            insertFavoriteCityInteractor = insertFavoriteCitiesInteractor,
        )
        val query = "query"
        presenter.test {
            var item = awaitItem()
            item.eventSink(SearchScreen.Event.QueryUpdated(TextFieldValue(query)))
            item = skipWhile { state -> state.citiesResult !is SearchScreen.CitiesResult.Loaded }
            val favorite = CityModelVancouver
            item.eventSink(SearchScreen.Event.FavoriteClick(favorite))
            val saved: FavoriteCity = insertFavoriteCitiesInteractor.cities.awaitItem()
            assertThat(saved.name).isEqualTo(favorite.name)
            assertThat(saved.countryCode).isEqualTo(favorite.countryCode)
        }
    }

    @Test
    @DisplayName("Presenter marks cities as favorites")
    fun presenterMarksFavoriteCities() = runTest {
        val citiesInteractor = FakeGetCitiesInteractor().apply {
            citiesResult.add(listOf(DomainCityVancouver, DomainCityBarcelona))
        }
        val favoriteCitiesInteractor = FakeGetFavoriteCitiesInteractor().apply {
            favoriteCities.add(listOf(FavoriteCityVancouver))
        }
        val presenter = getPresenter(
            getCitiesInteractor = citiesInteractor,
            getFavoriteCitiesInteractor = favoriteCitiesInteractor,
        )
        val query = "query"
        presenter.test {
            var item = awaitItem()
            item.eventSink(SearchScreen.Event.QueryUpdated(TextFieldValue(query)))
            item = skipWhile { state -> state.citiesResult !is SearchScreen.CitiesResult.Loaded }
            val favorites = (item.citiesResult as SearchScreen.CitiesResult.Loaded).cities.filter { it.isFavorite }
            assertThat(favorites.size).isEqualTo(1)
            assertThat(favorites.first().name).isEqualTo(FavoriteCityVancouver.name)
            assertThat(favorites.first().countryCode).isEqualTo(FavoriteCityVancouver.countryCode)
        }
    }

    @Test
    @DisplayName("Presenter removes city from favorites")
    fun presenterRemovesFavoriteCities() = runTest {
        val citiesInteractor = FakeGetCitiesInteractor().apply {
            citiesResult.add(listOf(DomainCityVancouver, DomainCityBarcelona))
        }
        val favoriteCitiesInteractor = FakeGetFavoriteCitiesInteractor().apply {
            favoriteCities.add(listOf(FavoriteCityVancouver))
        }
        val deleteFavoriteCitiesInteractor = FakeDeleteFavoriteCityInteractor()
        val presenter = getPresenter(
            getCitiesInteractor = citiesInteractor,
            getFavoriteCitiesInteractor = favoriteCitiesInteractor,
            deleteFavoriteCityInteractor = deleteFavoriteCitiesInteractor,
        )
        val query = "query"
        presenter.test {
            var item = awaitItem()
            item.eventSink(SearchScreen.Event.QueryUpdated(TextFieldValue(query)))
            item = skipWhile { state -> state.citiesResult !is SearchScreen.CitiesResult.Loaded }
            val loaded = item.citiesResult as SearchScreen.CitiesResult.Loaded
            val vancouver = loaded.cities.first { it.name == VancouverCityName }
            item.eventSink(SearchScreen.Event.FavoriteClick(vancouver))
            val deleted = deleteFavoriteCitiesInteractor.cities.awaitItem()
            assertThat(deleted).isEqualTo(FavoriteCityVancouver)
        }
    }

    private suspend fun ReceiveTurbine<SearchScreen.State>.skipWhile(
        predicate: (SearchScreen.State) -> Boolean,
    ): SearchScreen.State {
        var item: SearchScreen.State
        do {
            item = awaitItem()
        } while (predicate(item))
        return item
    }

    private fun getPresenter(
        navigator: Navigator = FakeNavigator(),
        getCitiesInteractor: GetCitiesInteractor = FakeGetCitiesInteractor(),
        insertFavoriteCityInteractor: InsertFavoriteCityInteractor = FakeInsertFavoriteCityInteractor(),
        deleteFavoriteCityInteractor: DeleteFavoriteCityInteractor = FakeDeleteFavoriteCityInteractor(),
        getFavoriteCitiesInteractor: GetFavoriteCitiesInteractor = FakeGetFavoriteCitiesInteractor().apply {
            favoriteCities.add(emptyList())
        },
        getRecentCitiesInteractor: GetRecentCitiesInteractor = FakeGetRecentCitiesInteractor(),
        insertRecentCitiesInteractor: InsertRecentCityInteractor = FakeInsertRecentCityInterator(),
        deleteRecentCityInteractor: DeleteRecentCityInteractor = FakeDeleteRecentCityInteractor(),
        dispatcherProvider: DispatcherProvider = TestDispatcherProvider(),
    ): SearchPresenter = SearchPresenter(
        navigator = navigator,
        getCitiesInteractor = getCitiesInteractor,
        insertFavoriteCityInteractor = insertFavoriteCityInteractor,
        deleteFavoriteCityInteractor = deleteFavoriteCityInteractor,
        getFavoriteCitiesInteractor = getFavoriteCitiesInteractor,
        getRecentCitiesInteractor = getRecentCitiesInteractor,
        insertRecentCitiesInteractor = insertRecentCitiesInteractor,
        deleteRecentCityInteractor = deleteRecentCityInteractor,
        dispatcherProvider = dispatcherProvider,
    )
}
