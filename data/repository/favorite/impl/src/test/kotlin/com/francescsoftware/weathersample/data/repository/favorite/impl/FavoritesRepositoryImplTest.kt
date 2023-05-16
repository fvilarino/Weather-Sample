package com.francescsoftware.weathersample.data.repository.favorite.impl

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCitiesDao
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCitiesDatabase
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCityEntity
import com.francescsoftware.weathersample.data.repository.favorites.api.FavoriteCity
import com.google.common.truth.Truth
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
        val city1 = FavoriteCityEntity(id = 1, name = "Barcelona", countryCode = "ES")
        val city2 = FavoriteCityEntity(id = 0, name = "Vancouver", countryCode = "CA")
        dao.insertFavoriteCity(city1)
        dao.insertFavoriteCity(city2)
        val cities = repository.getFavoriteCities().first()
        Truth.assertThat(cities.size).isEqualTo(2)
        Truth.assertThat(cities[0]).isEqualTo(city1.toFavoriteCity())
        Truth.assertThat(cities[1]).isEqualTo(city2.toFavoriteCity())
    }

    @Test
    fun `get favorite cities emits on update`() = runTest {
        repository.getFavoriteCities().test {
            val barcelona = FavoriteCityEntity(id = 0, name = "Barcelona", countryCode = "ES")
            val vancouver = FavoriteCityEntity(id = 1, name = "Vancouver", countryCode = "CA")
            Truth.assertThat(awaitItem()).isEmpty()
            dao.insertFavoriteCity(barcelona)
            var items = awaitItem()
            Truth.assertThat(items.size).isEqualTo(1)
            Truth.assertThat(items[0]).isEqualTo(barcelona.toFavoriteCity())
            dao.insertFavoriteCity(vancouver)
            items = awaitItem()
            Truth.assertThat(items.size).isEqualTo(2)
            Truth.assertThat(items[0]).isEqualTo(barcelona.toFavoriteCity())
            Truth.assertThat(items[1]).isEqualTo(vancouver.toFavoriteCity())
            dao.deleteFavoriteCity(favoriteCityEntity = vancouver)
            items = awaitItem()
            Truth.assertThat(items.size).isEqualTo(1)
            Truth.assertThat(items[0]).isEqualTo(barcelona.toFavoriteCity())
        }
    }

    @Test
    fun `get favorite cities sorts cities by name`() = runTest {
        val vancouver = FavoriteCityEntity(id = 0, name = "Vancouver", countryCode = "CA")
        val barcelona = FavoriteCityEntity(id = 1, name = "Barcelona", countryCode = "ES")
        dao.insertFavoriteCity(vancouver)
        dao.insertFavoriteCity(barcelona)
        val items = repository.getFavoriteCities().first()
        Truth.assertThat(items.size).isEqualTo(2)
        Truth.assertThat(items[0]).isEqualTo(barcelona.toFavoriteCity())
        Truth.assertThat(items[1]).isEqualTo(vancouver.toFavoriteCity())
    }

    @Test
    fun `favorite city is inserted into database`() = runTest {
        val vancouver = FavoriteCity(id = 0, name = "Vancouver", countryCode = "CA")
        repository.saveFavoriteCity(favoriteCity = vancouver)
        val entries = dao.getFavoriteCities().first()
        Truth.assertThat(entries.first()).isEqualTo(vancouver.toFavoriteCityEntity())
    }

    @Test
    fun `favorite city is deleted from database`() = runTest {
        val vancouver = FavoriteCityEntity(id = 0, name = "Vancouver", countryCode = "CA")
        dao.insertFavoriteCity(vancouver)

        repository.deleteFavoriteCity(favoriteCity = vancouver.toFavoriteCity())
        val entries = dao.getFavoriteCities().first()
        Truth.assertThat(entries).isEmpty()
    }

    private fun FavoriteCityEntity.toFavoriteCity(): FavoriteCity = FavoriteCity(
        id = id,
        name = name,
        countryCode = countryCode,
    )

    private fun FavoriteCity.toFavoriteCityEntity(): FavoriteCityEntity = FavoriteCityEntity(
        id = id,
        name = name,
        countryCode = countryCode,
    )
}
