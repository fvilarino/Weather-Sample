package com.francescsoftware.weathersample.core.time.impl

import com.francescsoftware.weathersample.core.time.api.ZoneIdProvider
import java.time.ZoneId
import javax.inject.Inject

internal class ZoneIdProviderImpl @Inject constructor() : ZoneIdProvider {
    override val zoneId: ZoneId
        get() = ZoneId.systemDefault()
}
