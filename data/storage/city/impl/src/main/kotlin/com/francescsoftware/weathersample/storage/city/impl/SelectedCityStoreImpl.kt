package com.francescsoftware.weathersample.storage.city.impl

import androidx.datastore.core.DataStore
import com.francescsoftware.weathersample.storage.city.api.SelectedCity
import com.francescsoftware.weathersample.storage.city.api.SelectedCityStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.francescsoftware.weathersample.storage.city.impl.SelectedCity as StoreSelectedCity

internal class SelectedCityStoreImpl @Inject constructor(
    private val cityStore: DataStore<StoreSelectedCity>,
) : SelectedCityStore {

    override val city: Flow<SelectedCity>
        get() = cityStore.data
            .map { data ->
                SelectedCity(
                    name = data.name.orEmpty(),
                    country = data.country.orEmpty(),
                    countryCode = data.countryCode.orEmpty(),
                )
            }

    override suspend fun setSelectedCity(
        name: String,
        country: String,
        countryCode: String,
    ) {
        cityStore.updateData { city ->
            city.toBuilder()
                .setName(name)
                .setCountry(country)
                .setCountryCode(countryCode)
                .build()
        }
    }
}
