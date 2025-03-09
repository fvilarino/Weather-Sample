package com.francescsoftware.weathersample.data.repository.city.impl

import android.util.LruCache
import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.data.network.safeApiCall
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.core.type.location.Coordinates
import com.francescsoftware.weathersample.data.repository.city.api.CitiesException
import com.francescsoftware.weathersample.data.repository.city.api.CityRepository
import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val CacheCapacity = 40

private data class PrefixCacheKey(
    val prefix: String,
    val offset: Int,
    val limit: Int,
)

private data class LocationCacheKey(
    val location: Coordinates,
    val offset: Int,
    val limit: Int,
)

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class CityRepositoryImpl @Inject constructor(
    private val cityService: CityService,
    private val dispatcherProvider: DispatcherProvider,
) : CityRepository {

    private val prefixCache = LruCache<PrefixCacheKey, CitySearchResponse>(CacheCapacity)
    private val locationCache = LruCache<LocationCacheKey, CitySearchResponse>(CacheCapacity)

    override suspend fun getCitiesByPrefix(
        prefix: String,
        offset: Int,
        limit: Int,
    ): CitySearchResponse {
        val prefixCacheKey = PrefixCacheKey(prefix = prefix, offset = offset, limit = limit)
        prefixCache[prefixCacheKey]?.let { return it }

        val networkResponse = safeApiCall {
            cityService.getCitiesByPrefix(prefix, offset, limit)
        }
        return networkResponse.fold(
            onSuccess = { response ->
                if (response.isValid) {
                    withContext(dispatcherProvider.default) {
                        response.toCityResponse().also { prefixCache.put(prefixCacheKey, it) }
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

    override suspend fun getCitiesByLocation(location: Coordinates, offset: Int, limit: Int): CitySearchResponse {
        val locationCacheKey = LocationCacheKey(location = location, offset = offset, limit = limit)
        locationCache[locationCacheKey]?.let { return it }

        val networkResponse = safeApiCall {
            cityService.getCitiesByLocation(location.asIso6709, offset, limit)
        }
        return networkResponse.fold(
            onSuccess = { response ->
                if (response.isValid) {
                    withContext(dispatcherProvider.default) {
                        response.toCityResponse().also { locationCache.put(locationCacheKey, it) }
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
