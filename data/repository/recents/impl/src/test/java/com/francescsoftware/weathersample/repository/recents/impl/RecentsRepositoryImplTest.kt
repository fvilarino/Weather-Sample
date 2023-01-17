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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RecentsRepositoryImplTest {
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
        Assert.assertEquals(2, cities.size)
        Assert.assertEquals(RecentCity(name = city1.name), cities[0])
        Assert.assertEquals(RecentCity(name = city2.name), cities[1])
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
        Assert.assertEquals(3, cities.size)
        Assert.assertEquals(RecentCity(name = city2.name), cities[0])
        Assert.assertEquals(RecentCity(name = city3.name), cities[1])
        Assert.assertEquals(RecentCity(name = city1.name), cities[2])
    }

    @Test
    fun `get recent cities returns at most the liit requested`() = runTest {
        val totalCities = 10
        repeat(totalCities) {
            val city = RecentCityEntity(name = "city_$it", lastUsed = it.toLong())
            dao.insertRecentCity(city)
        }
        var cities = repository.getRecentCities(limit = 4).first()
        Assert.assertEquals(4, cities.size)
        cities = repository.getRecentCities(limit = 20).first()
        Assert.assertEquals(totalCities, cities.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get recent cities emits on update`() = runTest {
        repository.getRecentCities(limit = 10).test {
            Assert.assertEquals(0, awaitItem().size)
            val city1 = RecentCityEntity(name = "Vancouver", lastUsed = 2L)
            dao.insertRecentCity(city1)
            Assert.assertEquals(1, awaitItem().size)
            val city2 = RecentCityEntity(name = "Barcelona", lastUsed = 1L)
            dao.insertRecentCity(city2)
            Assert.assertEquals(2, awaitItem().size)
            dao.deleteRecentCity(name = city1.name)
            Assert.assertEquals(1, awaitItem().size)
        }
    }

    @Test
    fun `recent city is inserted into database`() = runTest {
        val recentCity = RecentCity("Vancouver")
        repository.saveRecentCity(recentCity = recentCity)
        val entries = dao.getCities(limit = 1).first()
        Assert.assertEquals(recentCity.name, entries.first().name)
    }

    @Test
    fun `recent city is deleted from database`() = runTest {
        val recentCity = RecentCity(name = "Vancouver")
        val city = RecentCityEntity(name = recentCity.name, lastUsed = 0L)
        dao.insertRecentCity(city)

        repository.deleteRecentCity(recentCity = recentCity)
        val entries = dao.getCities(limit = 10).first()
        Assert.assertTrue(entries.isEmpty())
    }
}
