package com.francescsoftware.weathersample.presentation.shared.lookup

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class LookupModule {
    @Binds
    @Reusable
    abstract fun bindStringLookup(stringLookupImpl: StringLookupImpl): StringLookup
}
