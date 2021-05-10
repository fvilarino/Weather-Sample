package com.francescsoftware.weathersample.storage.selectedcity

import androidx.datastore.core.DataStore
import com.francescsoftware.weathersample.presentation.storage.SelectedCity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SelectedCityStoreImpl @Inject constructor(
    private val cityStore: DataStore<SelectedCity>,
) : SelectedCityStore {

    override val city: Flow<SelectedCity>
        get() = cityStore.data

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
