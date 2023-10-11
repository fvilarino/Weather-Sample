package com.francescsoftware.weathersample.core.time.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.time.api.ZoneIdProvider
import com.squareup.anvil.annotations.ContributesBinding
import java.time.ZoneId
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class ZoneIdProviderImpl @Inject constructor() : ZoneIdProvider {
    override val zoneId: ZoneId
        get() = ZoneId.systemDefault()
}
