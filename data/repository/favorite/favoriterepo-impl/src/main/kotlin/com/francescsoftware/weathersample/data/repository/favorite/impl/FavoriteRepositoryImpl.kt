package com.francescsoftware.weathersample.data.repository.favorite.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCitiesDao
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCitiesDatabase
import com.francescsoftware.weathersample.data.repository.favorite.impl.dao.FavoriteCityEntity
import com.francescsoftware.weathersample.data.repository.favorites.api.FavoriteCity
import com.francescsoftware.weathersample.data.repository.favorites.api.FavoriteRepository
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteCitiesDatabase: FavoriteCitiesDatabase,
) : FavoriteRepository {

    private val dao: FavoriteCitiesDao
        get() = favoriteCitiesDatabase.favoriteCitiesDao()

    override fun getFavoriteCities(): Flow<List<FavoriteCity>> = dao.getFavoriteCities().map { cities ->
        cities.map { city ->
            FavoriteCity(
                cityId = city.cityId,
                name = city.name,
                countryCode = city.countryCode,
            )
        }
    }

    override suspend fun saveFavoriteCity(favoriteCity: FavoriteCity) = dao.insertFavoriteCity(
        FavoriteCityEntity(
            cityId = favoriteCity.cityId,
            name = favoriteCity.name,
            countryCode = favoriteCity.countryCode,
        ),
    )

    override suspend fun deleteFavoriteCity(cityId: Long) = dao.deleteFavoriteCity(
        cityId,
    )
}
