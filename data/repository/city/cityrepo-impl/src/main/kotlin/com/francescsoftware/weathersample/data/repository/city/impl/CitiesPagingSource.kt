package com.francescsoftware.weathersample.data.repository.city.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.francescsoftware.weathersample.data.repository.city.api.CitiesException
import com.francescsoftware.weathersample.data.repository.city.api.CityRepository
import com.francescsoftware.weathersample.data.repository.city.api.model.City
import java.util.concurrent.CancellationException

internal class CitiesPagingSource(
    private val prefix: String,
    private val cityRepository: CityRepository,
) : PagingSource<Int, City>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> = try {
        val offset = params.key ?: 0
        val response = cityRepository.getCities(
            prefix = prefix,
            offset = offset,
            limit = params.loadSize,
        )
        val hasMore = response.metadata.currentOffset + params.loadSize < response.metadata.totalCount
        LoadResult.Page(
            data = response.cities,
            prevKey = if (response.metadata.currentOffset > 0) response.metadata.currentOffset else null,
            nextKey = if (hasMore) response.metadata.currentOffset + params.loadSize else null,
        )
    } catch (ex: CancellationException) {
        throw ex
    } catch (e: CitiesException) {
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<Int, City>): Int? = state.anchorPosition?.let { anchorPosition ->
        state.closestPageToPosition(anchorPosition)?.prevKey?.plus(state.config.pageSize)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(state.config.pageSize)
    }
}
