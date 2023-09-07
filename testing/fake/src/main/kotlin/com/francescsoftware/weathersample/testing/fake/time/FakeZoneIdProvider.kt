package com.francescsoftware.weathersample.testing.fake.time

import com.francescsoftware.weathersample.core.time.api.ZoneIdProvider
import java.time.ZoneId

/** An implementation of [ZoneIdProvider] to be used in tests */
class FakeZoneIdProvider : ZoneIdProvider {
    /** The [ZoneId] to return */
    var currentZoneId: ZoneId = ZoneId.of("UTC")

    /** @{inheritDoc} */
    override val zoneId: ZoneId
        get() = currentZoneId
}
