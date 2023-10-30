package com.francescsoftware.weathersample.data.repository.favorite.impl

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCitiesDao
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCitiesDatabase
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCityEntity
import com.francescsoftware.weathersample.data.repository.favorites.api.FavoriteCity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
internal class FavoritesRepositoryImplTest {
    private lateinit var favoriteCitiesDatabase: FavoriteCitiesDatabase
    private lateinit var dao: FavoriteCitiesDao
    private lateinit var repository: FavoriteRepositoryImpl

    @BeforeEach
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        favoriteCitiesDatabase = Room.inMemoryDatabaseBuilder(context, FavoriteCitiesDatabase::class.java).build()
        dao = favoriteCitiesDatabase.favoriteCitiesDao()
        repository = FavoriteRepositoryImpl(favoriteCitiesDatabase = favoriteCitiesDatabase)
    }

    @AfterEach
    fun clear() {
        favoriteCitiesDatabase.close()
    }

    @Test
    fun `get favorite cities returns database data`() = runTest {
        val city1 = FavoriteCityEntity(cityId = 1, name = "Barcelona", countryCode = "ES")
        val city2 = FavoriteCityEntity(cityId = 0, name = "Vancouver", countryCode = "CA")
        dao.insertFavoriteCity(city1)
        dao.insertFavoriteCity(city2)
        val cities = repository.getFavoriteCities().first()
        assertThat(cities.size).isEqualTo(2)
        assertThat(cities[0]).isEqualTo(city1.toFavoriteCity())
        assertThat(cities[1]).isEqualTo(city2.toFavoriteCity())
    }

    @Test
    fun `get favorite cities emits on update`() = runTest {
        repository.getFavoriteCities().test {
            val barcelona = FavoriteCityEntity(cityId = 0, name = "Barcelona", countryCode = "ES")
            val vancouver = FavoriteCityEntity(cityId = 1, name = "Vancouver", countryCode = "CA")
            assertThat(awaitItem()).isEmpty()
            dao.insertFavoriteCity(barcelona)
            var items = awaitItem()
            assertThat(items.size).isEqualTo(1)
            assertThat(items[0]).isEqualTo(barcelona.toFavoriteCity())
            dao.insertFavoriteCity(vancouver)
            items = awaitItem()
            assertThat(items.size).isEqualTo(2)
            assertThat(items[0]).isEqualTo(barcelona.toFavoriteCity())
            assertThat(items[1]).isEqualTo(vancouver.toFavoriteCity())
            dao.deleteFavoriteCity(cityId = vancouver.cityId)
            items = awaitItem()
            assertThat(items.size).isEqualTo(1)
            assertThat(items[0]).isEqualTo(barcelona.toFavoriteCity())
        }
    }

    @Test
    fun `get favorite cities sorts cities by name`() = runTest {
        val vancouver = FavoriteCityEntity(cityId = 0, name = "Vancouver", countryCode = "CA")
        val barcelona = FavoriteCityEntity(cityId = 1, name = "Barcelona", countryCode = "ES")
        dao.insertFavoriteCity(vancouver)
        dao.insertFavoriteCity(barcelona)
        val items = repository.getFavoriteCities().first()
        assertThat(items.size).isEqualTo(2)
        assertThat(items[0]).isEqualTo(barcelona.toFavoriteCity())
        assertThat(items[1]).isEqualTo(vancouver.toFavoriteCity())
    }

    @Test
    fun `favorite city is inserted into database`() = runTest {
        val vancouver = FavoriteCity(cityId = 0, name = "Vancouver", countryCode = "CA")
        repository.saveFavoriteCity(favoriteCity = vancouver)
        val entries = dao.getFavoriteCities().first()
        assertThat(entries.first()).isEqualTo(vancouver.toFavoriteCityEntity())
    }

    @Test
    fun `favorite city is deleted from database`() = runTest {
        val vancouver = FavoriteCityEntity(cityId = 0, name = "Vancouver", countryCode = "CA")
        dao.insertFavoriteCity(vancouver)

        repository.deleteFavoriteCity(cityId = vancouver.cityId)
        val entries = dao.getFavoriteCities().first()
        assertThat(entries).isEmpty()
    }

    private fun FavoriteCityEntity.toFavoriteCity(): FavoriteCity = FavoriteCity(
        cityId = cityId,
        name = name,
        countryCode = countryCode,
    )

    private fun FavoriteCity.toFavoriteCityEntity(): FavoriteCityEntity = FavoriteCityEntity(
        cityId = cityId,
        name = name,
        countryCode = countryCode,
    )
}
