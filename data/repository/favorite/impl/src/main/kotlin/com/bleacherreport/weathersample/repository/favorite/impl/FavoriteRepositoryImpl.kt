package com.bleacherreport.weathersample.repository.favorite.impl

import com.bleacherreport.weathersample.repository.favorite.impl.dao.FavoriteCitiesDao
import com.bleacherreport.weathersample.repository.favorite.impl.dao.FavoriteCitiesDatabase
import com.bleacherreport.weathersample.repository.favorite.impl.dao.FavoriteCityEntity
import com.bleacherreport.weathersample.repository.favorites.api.FavoriteCity
import com.bleacherreport.weathersample.repository.favorites.api.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteCitiesDatabase: FavoriteCitiesDatabase,
) : FavoriteRepository {

    private val dao: FavoriteCitiesDao
        get() = favoriteCitiesDatabase.favoriteCitiesDao()

    override fun getFavoriteCities(): Flow<List<FavoriteCity>> = dao.getFavoriteCities().map { cities ->
        cities.map { city ->
            FavoriteCity(
                id = city.id,
                name = city.name,
                countryCode = city.countryCode,
            )
        }
    }

    override suspend fun saveFavoriteCity(favoriteCity: FavoriteCity) = dao.insertFavoriteCity(
        FavoriteCityEntity(
            id = 0,
            name = favoriteCity.name,
            countryCode = favoriteCity.countryCode,
        )
    )

    override suspend fun deleteFavoriteCity(favoriteCity: FavoriteCity) = dao.deleteFavoriteCity(
        FavoriteCityEntity(
            id = favoriteCity.id,
            name = favoriteCity.name,
            countryCode = favoriteCity.countryCode,
        )
    )
}
