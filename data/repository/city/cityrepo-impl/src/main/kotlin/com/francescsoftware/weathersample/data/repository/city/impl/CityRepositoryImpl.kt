package com.francescsoftware.weathersample.data.repository.city.impl

import android.util.LruCache
import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.core.network.safeApiCall
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.data.repository.city.api.CitiesException
import com.francescsoftware.weathersample.data.repository.city.api.CityRepository
import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val CacheCapacity = 40

private data class CacheKey(
    val prefix: String,
    val offset: Int,
    val limit: Int,
)

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class CityRepositoryImpl @Inject constructor(
    private val cityService: CityService,
    private val dispatcherProvider: DispatcherProvider,
) : CityRepository {

    private val cache = LruCache<CacheKey, CitySearchResponse>(CacheCapacity)

    override suspend fun getCities(
        prefix: String,
        offset: Int,
        limit: Int,
    ): CitySearchResponse {
        val cacheKey = CacheKey(prefix = prefix, offset = offset, limit = limit)
        cache[cacheKey]?.let { return it }

        val networkResponse = safeApiCall {
            cityService.getCities(prefix, offset, limit)
        }
        return networkResponse.fold(
            onSuccess = { response ->
                if (response.isValid) {
                    withContext(dispatcherProvider.default) {
                        response.toCityResponse().also { cache.put(cacheKey, it) }
                    }
                } else {
                    throw CitiesException("Invalid data received")
                }
            },
            onFailure = { throwable ->
                throw CitiesException(cause = throwable)
            },
        )
    }
}
