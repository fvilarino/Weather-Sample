package com.francescsoftware.weathersample.ui.feature.search.city.viewmodel

import app.cash.turbine.Turbine
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.io.IOException

internal class FakeGetCitiesInteractor : GetCitiesInteractor {
    val citiesResult = Turbine<List<City>?>()

    override suspend fun invoke(prefix: String, limit: Int): Either<Cities> {
        val cities = citiesResult.awaitItem()
        return if (cities == null) {
            Either.Failure(IOException("Failed to load cities"))
        } else {
            Either.Success(
                Cities(
                    metadata = Metadata(
                        offset = 0,
                        totalCount = cities.size,
                    ),
                    cities = cities,
                ),
            )
        }
    }
}

internal class FakeGetFavoriteCitiesInteractor : GetFavoriteCitiesInteractor {
    val favoriteCities = Turbine<List<FavoriteCity>>()

    override fun invoke(): Flow<List<FavoriteCity>> = favoriteCities.asChannel().receiveAsFlow()
}

internal class FakeInsertFavoriteCityInteractor : InsertFavoriteCityInteractor {
    val cities = Turbine<FavoriteCity>()

    override suspend fun invoke(city: FavoriteCity) {
        cities.add(city)
    }
}

internal class FakeDeleteFavoriteCityInteractor : DeleteFavoriteCityInteractor {
    val cities = Turbine<FavoriteCity>()

    override suspend fun invoke(city: FavoriteCity) {
        cities.add(city)
    }
}

internal class FakeGetRecentCitiesInteractor : GetRecentCitiesInteractor {
    val cities = Turbine<List<RecentCity>>()

    override fun invoke(limit: Int): Flow<List<RecentCity>> = cities.asChannel().receiveAsFlow()
}

internal class FakeInsertRecentCityInterator : InsertRecentCityInteractor {
    val cities = Turbine<RecentCity>()

    override suspend fun invoke(city: RecentCity) {
        cities.add(city)
    }
}

internal class FakeDeleteRecentCityInteractor : DeleteRecentCityInteractor {
    val cities = Turbine<RecentCity>()

    override suspend fun invoke(city: RecentCity) {
        cities.add(city)
    }
}
