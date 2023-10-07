package com.francescsoftware.weathersample.ui.feature.search.city.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.francescsoftware.weathersample.core.coroutines.CloseableCoroutineScope
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Cities
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Metadata
import com.francescsoftware.weathersample.testing.fake.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.Coordinates
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.SelectedCity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.io.IOException
import kotlin.time.Duration
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Coordinates as DomainCoordinates

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

private class FakeGetCitiesInteractor : GetCitiesInteractor {
    var prefix: String = ""
    var cities: List<City>? = null

    override suspend fun invoke(prefix: String, limit: Int): Either<Cities> = if (cities == null) {
        Either.Failure(IOException("Failed to load cities"))
    } else {
        if (this.prefix == prefix) {
            Either.Success(
                Cities(
                    metadata = Metadata(
                        offset = 0,
                        totalCount = cities!!.size,
                    ),
                    cities = cities!!,
                ),
            )
        } else {
            Either.Success(
                Cities(
                    metadata = Metadata(
                        offset = 0,
                        totalCount = 0,
                    ),
                    cities = emptyList(),
                ),
            )
        }
    }
}

private class FakeGetFavoriteCitiesInteractor : GetFavoriteCitiesInteractor {
    var favoriteCities: List<FavoriteCity> = emptyList()

    override fun invoke(): Flow<List<FavoriteCity>> {
        return flowOf(favoriteCities)
    }
}

private class FakeInsertFavoriteCityInteractor : InsertFavoriteCityInteractor {
    var lastCity: FavoriteCity? = null

    override suspend fun invoke(city: FavoriteCity) {
        lastCity = city
    }
}

private class FakeDeleteFavoriteCityInteractor : DeleteFavoriteCityInteractor {
    var lastCity: FavoriteCity? = null

    override suspend fun invoke(city: FavoriteCity) {
        lastCity = city
    }
}

private class FakeGetRecentCitiesInteractor : GetRecentCitiesInteractor {
    var cities: List<RecentCity> = emptyList()

    override fun invoke(limit: Int): Flow<List<RecentCity>> = flowOf(cities)
}

private class FakeInsertRecentCityInterator : InsertRecentCityInteractor {
    var lastCity: RecentCity? = null

    override suspend fun invoke(city: RecentCity) {
        lastCity = city
    }
}

private class FakeDeleteRecentCityInteractor : DeleteRecentCityInteractor {
    var lastCity: RecentCity? = null

    override suspend fun invoke(city: RecentCity) {
        lastCity = city
    }
}

private val DomainCityVancouver = City(
    id = VancouverCityId,
    name = VancouverCityName,
    region = VancouverCityRegion,
    regionCode = VancouverCityRegionCode,
    country = VancouverCityCountry,
    countryCode = VancouverCityCountryCode,
    coordinates = DomainCoordinates(
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
    coordinates = DomainCoordinates(
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
    coordinates = Coordinates(
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
    coordinates = Coordinates(
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

@OptIn(ExperimentalCoroutinesApi::class)
internal class CityViewModelTest {

    @Test
    fun `viewmodel loads cities from query`() = runTest {
        CloseableCoroutineScope(UnconfinedTestDispatcher()).use { scope ->
            val query = "query"
            val citiesInteractor = FakeGetCitiesInteractor().apply {
                prefix = query
                cities = listOf(DomainCityVancouver, DomainCityBarcelona)
            }
            val cityMiddleware = getCityMiddleware(
                getCitiesInteractor = citiesInteractor,
                scope = scope,
            )
            val recentCitiesMiddleware = getRecentCitiesMiddleware(
                scope = scope,
            )
            val viewModel = CityViewModel(
                closeableScope = scope,
                reducer = CityReducer(),
                middlewares = setOf(cityMiddleware, recentCitiesMiddleware),
            )
            viewModel.onStart()
            viewModel.onQueryChange(query = TextFieldValue(query))
            val state = viewModel.state
            assertThat(state.loadState).isEqualTo(LoadState.Loaded)
            assertThat(state.cities.size).isEqualTo(2)
            assertThat(state.cities[0]).isEqualTo(CityModelVancouver)
            assertThat(state.cities[1]).isEqualTo(CityModelBarcelona)
        }
    }

    @Test
    fun `viewmodel reports error when failing to load cities`() = runTest {
        CloseableCoroutineScope(UnconfinedTestDispatcher()).use { scope ->
            val query = "query"
            val cityMiddleware = getCityMiddleware(
                scope = scope,
            )
            val recentCitiesMiddleware = getRecentCitiesMiddleware(
                scope = scope,
            )
            val viewModel = CityViewModel(
                closeableScope = scope,
                reducer = CityReducer(),
                middlewares = setOf(cityMiddleware, recentCitiesMiddleware),
            )
            viewModel.onStart()
            viewModel.onQueryChange(query = TextFieldValue(query))
            val state = viewModel.state
            assertThat(state.loadState).isEqualTo(LoadState.Error)
            assertThat(state.cities.isEmpty()).isTrue()
        }
    }

    @Test
    fun `viewmodel provides recent cities when input field focused`() = runTest {
        CloseableCoroutineScope(UnconfinedTestDispatcher()).use { scope ->
            val recentsInteractor = FakeGetRecentCitiesInteractor().apply {
                cities = RecentCities
            }
            val recentCitiesMiddleware = getRecentCitiesMiddleware(
                getRecentCitiesInteractor = recentsInteractor,
                scope = scope,
            )
            val viewModel = CityViewModel(
                closeableScope = scope,
                reducer = CityReducer(),
                middlewares = setOf(
                    getCityMiddleware(scope = scope),
                    recentCitiesMiddleware,
                ),
            )
            viewModel.onStart()
            viewModel.onQueryFocused()
            val state = viewModel.state
            assertThat(state.recentCities.size).isEqualTo(RecentCities.size)
            assertk.assertAll {
                state.recentCities.forEachIndexed { index, recent ->
                    assertThat(recent.name).isEqualTo(RecentCities[index].name)
                }
            }
        }
    }

    @Test
    fun `clicking on a city chip populates the query and triggers a load`() = runTest {
        CloseableCoroutineScope(UnconfinedTestDispatcher()).use { scope ->
            val chipCity = RecentCityVancouver
            val citiesInteractor = FakeGetCitiesInteractor().apply {
                prefix = chipCity
                cities = listOf(DomainCityVancouver, DomainCityBarcelona)
            }
            val cityMiddleware = getCityMiddleware(
                getCitiesInteractor = citiesInteractor,
                scope = scope,
            )
            val recentsInteractor = FakeGetRecentCitiesInteractor().apply {
                cities = RecentCities
            }
            val recentCitiesMiddleware = getRecentCitiesMiddleware(
                getRecentCitiesInteractor = recentsInteractor,
                scope = scope,
            )
            val viewModel = CityViewModel(
                closeableScope = scope,
                reducer = CityReducer(),
                middlewares = setOf(cityMiddleware, recentCitiesMiddleware),
            )
            viewModel.onStart()
            viewModel.onQueryFocused()
            viewModel.onChipClick(RecentCityModel(chipCity))
            val state = viewModel.state
            assertThat(state.cities.size).isEqualTo(2)
            assertThat(state.cities[0]).isEqualTo(CityModelVancouver)
            assertThat(state.cities[1]).isEqualTo(CityModelBarcelona)
        }
    }

    @Test
    fun `clicking on a city adds it as a recent`() = runTest {
        CloseableCoroutineScope(UnconfinedTestDispatcher()).use { scope ->
            val chipCity = RecentCityVancouver
            val citiesInteractor = FakeGetCitiesInteractor().apply {
                prefix = chipCity
                cities = listOf(DomainCityVancouver, DomainCityBarcelona)
            }
            val insertRecentCitiesInteractor = FakeInsertRecentCityInterator()
            val cityMiddleware = getCityMiddleware(
                getCitiesInteractor = citiesInteractor,
                scope = scope,
            )
            val recentCitiesMiddleware = getRecentCitiesMiddleware(
                insertRecentCitiesInteractor = insertRecentCitiesInteractor,
                scope = scope,
            )
            val viewModel = CityViewModel(
                closeableScope = scope,
                reducer = CityReducer(),
                middlewares = setOf(cityMiddleware, recentCitiesMiddleware),
            )
            viewModel.onStart()
            viewModel.onQueryFocused()
            viewModel.onCityClick(
                SelectedCity(
                    name = DomainCityVancouver.name,
                    countryCode = DomainCityVancouver.countryCode,
                ),
            )
            assertThat(insertRecentCitiesInteractor.lastCity?.name).isNotNull().isEqualTo(RecentCityVancouver)
        }
    }

    @Test
    fun `removing a city chip triggers a database removal`() = runTest {
        CloseableCoroutineScope(UnconfinedTestDispatcher()).use { scope ->
            val chipCity = RecentCityVancouver
            val citiesInteractor = FakeGetCitiesInteractor().apply {
                prefix = chipCity
                cities = listOf(DomainCityVancouver, DomainCityBarcelona)
            }
            val deleteRecentCityInteractor = FakeDeleteRecentCityInteractor()
            val cityMiddleware = getCityMiddleware(
                getCitiesInteractor = citiesInteractor,
                scope = scope,
            )
            val recentsInteractor = FakeGetRecentCitiesInteractor().apply {
                cities = RecentCities
            }
            val recentCitiesMiddleware = getRecentCitiesMiddleware(
                getRecentCitiesInteractor = recentsInteractor,
                deleteRecentCityInteractor = deleteRecentCityInteractor,
                scope = scope,
            )
            val viewModel = CityViewModel(
                closeableScope = scope,
                reducer = CityReducer(),
                middlewares = setOf(cityMiddleware, recentCitiesMiddleware),
            )
            viewModel.onStart()
            viewModel.onQueryFocused()
            viewModel.onDeleteChip(RecentCityModel(chipCity))
            assertThat(deleteRecentCityInteractor.lastCity?.name).isNotNull().isEqualTo(RecentCityVancouver)
        }
    }

    @Test
    fun `viewmodel sets favorite cities as favorites`() = runTest {
        CloseableCoroutineScope(UnconfinedTestDispatcher()).use { scope ->
            val query = "query"
            val citiesInteractor = FakeGetCitiesInteractor().apply {
                prefix = query
                cities = listOf(DomainCityVancouver, DomainCityBarcelona)
            }
            val favoriteCityInteractor = FakeGetFavoriteCitiesInteractor().apply {
                favoriteCities = listOf(FavoriteCityVancouver)
            }
            val cityMiddleware = getCityMiddleware(
                getCitiesInteractor = citiesInteractor,
                getFavoriteCitiesInteractor = favoriteCityInteractor,
                scope = scope,
            )
            val recentCitiesMiddleware = getRecentCitiesMiddleware(
                scope = scope,
            )
            val viewModel = CityViewModel(
                closeableScope = scope,
                reducer = CityReducer(),
                middlewares = setOf(cityMiddleware, recentCitiesMiddleware),
            )
            viewModel.onStart()
            viewModel.onQueryChange(query = TextFieldValue(query))
            val state = viewModel.state
            assertThat(state.cities.size).isEqualTo(2)
            assertThat(state.cities[0].isFavorite).isTrue()
            assertThat(state.cities[1].isFavorite).isFalse()
        }
    }

    @Test
    fun `clicking on favorite sets the city as favorite`() = runTest {
        CloseableCoroutineScope(UnconfinedTestDispatcher()).use { scope ->
            val query = "query"
            val citiesInteractor = FakeGetCitiesInteractor().apply {
                prefix = query
                cities = listOf(DomainCityVancouver, DomainCityBarcelona)
            }
            val insertFavoriteCityInteractor = FakeInsertFavoriteCityInteractor()
            val cityMiddleware = getCityMiddleware(
                getCitiesInteractor = citiesInteractor,
                insertFavoriteCityInteractor = insertFavoriteCityInteractor,
                scope = scope,
            )
            val viewModel = CityViewModel(
                closeableScope = scope,
                reducer = CityReducer(),
                middlewares = setOf(
                    cityMiddleware,
                    getRecentCitiesMiddleware(
                        scope = scope,
                    ),
                ),
            )
            viewModel.onStart()
            viewModel.onQueryChange(query = TextFieldValue(query))
            viewModel.onFavoriteClick(cityResultModel = CityModelVancouver)
            assertThat(insertFavoriteCityInteractor.lastCity).isNotNull().isEqualTo(FavoriteCityVancouver.copy(id = 0))
        }
    }

    @Test
    fun `clicking on unfavorite unsets the city as favorite`() = runTest {
        CloseableCoroutineScope(UnconfinedTestDispatcher()).use { scope ->
            val query = "query"
            val citiesInteractor = FakeGetCitiesInteractor().apply {
                prefix = query
                cities = listOf(DomainCityVancouver, DomainCityBarcelona)
            }
            val favoriteCityInteractor = FakeGetFavoriteCitiesInteractor().apply {
                favoriteCities = listOf(FavoriteCityVancouver)
            }
            val deleteFavoriteCityInteractor = FakeDeleteFavoriteCityInteractor()
            val cityMiddleware = getCityMiddleware(
                getCitiesInteractor = citiesInteractor,
                getFavoriteCitiesInteractor = favoriteCityInteractor,
                deleteFavoriteCityInteractor = deleteFavoriteCityInteractor,
                scope = scope,
            )
            val viewModel = CityViewModel(
                closeableScope = scope,
                reducer = CityReducer(),
                middlewares = setOf(
                    cityMiddleware,
                    getRecentCitiesMiddleware(
                        scope = scope,
                    ),
                ),
            )
            viewModel.onStart()
            viewModel.onQueryChange(query = TextFieldValue(query))
            viewModel.onFavoriteClick(cityResultModel = CityModelVancouver.copy(favoriteId = FavoriteCityVancouverId))
            assertThat(deleteFavoriteCityInteractor.lastCity).isNotNull().isEqualTo(FavoriteCityVancouver)
        }
    }

    private fun getCityMiddleware(
        getCitiesInteractor: GetCitiesInteractor = FakeGetCitiesInteractor(),
        getFavoriteCitiesInteractor: GetFavoriteCitiesInteractor = FakeGetFavoriteCitiesInteractor(),
        insertFavoriteCityInteractor: InsertFavoriteCityInteractor = FakeInsertFavoriteCityInteractor(),
        deleteFavoriteCityInteractor: DeleteFavoriteCityInteractor = FakeDeleteFavoriteCityInteractor(),
        scope: CloseableCoroutineScope,
    ): CityMiddleware = CityMiddleware(
        getCitiesInteractor = getCitiesInteractor,
        getFavoriteCitiesInteractor = getFavoriteCitiesInteractor,
        insertFavoriteCityInteractor = insertFavoriteCityInteractor,
        deleteFavoriteCityInteractor = deleteFavoriteCityInteractor,
        dispatcherProvider = TestDispatcherProvider(),
        scope = scope,
    ).apply { debounceDelay = Duration.ZERO }

    private fun getRecentCitiesMiddleware(
        getRecentCitiesInteractor: GetRecentCitiesInteractor = FakeGetRecentCitiesInteractor(),
        insertRecentCitiesInteractor: InsertRecentCityInteractor = FakeInsertRecentCityInterator(),
        deleteRecentCityInteractor: DeleteRecentCityInteractor = FakeDeleteRecentCityInteractor(),
        scope: CloseableCoroutineScope,
    ) = RecentCitiesMiddleware(
        getRecentCitiesInteractor = getRecentCitiesInteractor,
        insertRecentCitiesInteractor = insertRecentCitiesInteractor,
        deleteRecentCityInteractor = deleteRecentCityInteractor,
        dispatcherProvider = TestDispatcherProvider(),
        scope = scope,
    )
}
