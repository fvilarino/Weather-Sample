package com.francescsoftware.weathersample.repository.recents.impl

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.francescsoftware.weathersample.repository.recents.api.RecentCity
import com.francescsoftware.weathersample.repository.recents.impl.dao.RecentCitiesDao
import com.francescsoftware.weathersample.repository.recents.impl.dao.RecentCitiesDatabase
import com.francescsoftware.weathersample.repository.recents.impl.dao.RecentCityEntity
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
internal class RecentsRepositoryImplTest {
    private lateinit var recentCitiesDatabase: RecentCitiesDatabase
    private lateinit var dao: RecentCitiesDao
    private lateinit var repository: RecentsRepositoryImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        recentCitiesDatabase = Room.inMemoryDatabaseBuilder(context, RecentCitiesDatabase::class.java).build()
        dao = recentCitiesDatabase.recentCitiesDao()
        repository = RecentsRepositoryImpl(recentCitiesDatabase = recentCitiesDatabase)
    }

    @After
    fun clear() {
        recentCitiesDatabase.close()
    }

    @Test
    fun `get recent cities returns database data`() = runTest {
        val city1 = RecentCityEntity(name = "Vancouver", lastUsed = 2L)
        val city2 = RecentCityEntity(name = "Barcelona", lastUsed = 1L)
        dao.insertRecentCity(city1)
        dao.insertRecentCity(city2)
        val cities = repository.getRecentCities(limit = 10).first()
        Truth.assertThat(cities.size).isEqualTo(2)
        Truth.assertThat(cities[0]).isEqualTo(RecentCity(name = city1.name))
        Truth.assertThat(cities[1]).isEqualTo(RecentCity(name = city2.name))
    }

    @Test
    fun `get recent cities are sorted by most recently used`() = runTest {
        val city1 = RecentCityEntity(name = "Vancouver", lastUsed = 0L)
        val city2 = RecentCityEntity(name = "Barcelona", lastUsed = 2L)
        val city3 = RecentCityEntity(name = "Tokyo", lastUsed = 1L)
        dao.insertRecentCity(city1)
        dao.insertRecentCity(city2)
        dao.insertRecentCity(city3)
        val cities = repository.getRecentCities(limit = 10).first()
        Truth.assertThat(cities.size).isEqualTo(3)
        Truth.assertThat(cities[0]).isEqualTo(RecentCity(name = city2.name))
        Truth.assertThat(cities[1]).isEqualTo(RecentCity(name = city3.name))
        Truth.assertThat(cities[2]).isEqualTo(RecentCity(name = city1.name))
    }

    @Test
    fun `get recent cities returns at most the max number requested`() = runTest {
        val totalCities = 10
        repeat(totalCities) {
            val city = RecentCityEntity(name = "city_$it", lastUsed = it.toLong())
            dao.insertRecentCity(city)
        }
        var cities = repository.getRecentCities(limit = 4).first()
        Truth.assertThat(cities.size).isEqualTo(4)
        cities = repository.getRecentCities(limit = 20).first()
        Truth.assertThat(cities.size).isEqualTo(totalCities)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get recent cities emits on update`() = runTest {
        repository.getRecentCities(limit = 10).test {
            val vancouver = "Vancouver"
            val barcelona = "Barcelona"
            Truth.assertThat(awaitItem()).isEmpty()
            val city1 = RecentCityEntity(name = vancouver, lastUsed = 2L)
            dao.insertRecentCity(city1)
            var items = awaitItem()
            Truth.assertThat(items.size).isEqualTo(1)
            Truth.assertThat(items[0].name).isEqualTo(vancouver)
            val city2 = RecentCityEntity(name = barcelona, lastUsed = 1L)
            dao.insertRecentCity(city2)
            items = awaitItem()
            Truth.assertThat(items.size).isEqualTo(2)
            Truth.assertThat(items[0].name).isEqualTo(vancouver)
            Truth.assertThat(items[1].name).isEqualTo(barcelona)
            dao.deleteRecentCity(name = city1.name)
            items = awaitItem()
            Truth.assertThat(items.size).isEqualTo(1)
            Truth.assertThat(items[0].name).isEqualTo(barcelona)
        }
    }

    @Test
    fun `recent city is inserted into database`() = runTest {
        val recentCity = RecentCity("Vancouver")
        repository.saveRecentCity(recentCity = recentCity)
        val entries = dao.getCities(limit = 1).first()
        Truth.assertThat(entries.first().name).isEqualTo(recentCity.name)
    }

    @Test
    fun `recent city is deleted from database`() = runTest {
        val recentCity = RecentCity(name = "Vancouver")
        val city = RecentCityEntity(name = recentCity.name, lastUsed = 0L)
        dao.insertRecentCity(city)

        repository.deleteRecentCity(recentCity = recentCity)
        val entries = dao.getCities(limit = 10).first()
        Truth.assertThat(entries).isEmpty()
    }
}
