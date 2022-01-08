package com.francescsoftware.weathersample.storage.city.api

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.properties.Delegates

class FakeSelectedCityStorage : SelectedCityStore {
    private var _city: SelectedCity? by Delegates.observable(null) { _, _, new ->
        if (new != null) {
            city.tryEmit(new)
        }
    }

    override val city = MutableSharedFlow<SelectedCity>()

    override suspend fun setSelectedCity(name: String, country: String, countryCode: String) {
        _city = SelectedCity(
            name = name,
            country = country,
            countryCode = countryCode,
        )
    }
}
