package com.francescsoftware.weathersample.data.repository.city.impl

import android.util.LruCache
import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.core.network.safeApiCall
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.data.repository.city.api.CitiesException
import com.francescsoftware.weathersample.data.repository.city.api.CityRepository
import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val CacheCapacity = 10

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class CityRepositoryImpl @Inject constructor(
    private val cityService: CityService,
    private val dispatcherProvider: DispatcherProvider,
) : CityRepository {

    private val cache = LruCache<String, CitySearchResponse>(CacheCapacity)

    override suspend fun getCities(
        prefix: String,
        limit: Int,
    ): Either<CitySearchResponse> {
        val cached = cache.get(prefix)
        if (cached != null) {
            return Either.Success(cached)
        }
        val networkResponse = safeApiCall {
            cityService.getCities(prefix, limit)
        }
        return networkResponse.fold(
            onSuccess = { response ->
                if (response.isValid) {
                    withContext(dispatcherProvider.default) {
                        Either.Success(response.toCityResponse().also { cache.put(prefix, it) })
                    }
                } else {
                    Either.Failure(
                        CitiesException("Invalid data received"),
                    )
                }
            },
            onFailure = { throwable ->
                Either.Failure(CitiesException(cause = throwable))
            },
        )
    }
}
