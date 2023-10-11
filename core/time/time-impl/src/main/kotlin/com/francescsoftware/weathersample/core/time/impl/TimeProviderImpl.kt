package com.francescsoftware.weathersample.core.time.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.time.api.TimeProvider
import com.squareup.anvil.annotations.ContributesBinding
import java.time.Instant
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class TimeProviderImpl @Inject constructor() : TimeProvider {
    override val epoch: Instant
        get() = Instant.now()
}
