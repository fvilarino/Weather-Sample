package com.francescsoftware.weathersample.ui.feature.search.city.viewmodel

import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ViewModelComponent::class)
internal interface CityMiddlewareModule {

    @Binds
    @IntoSet
    fun bindCityMiddleware(
        cityMiddleware: CityMiddleware,
    ): Middleware<CityState, CityAction>

    @Binds
    @IntoSet
    fun bindRecentCitiesMiddleware(
        recentCitiesMiddleware: RecentCitiesMiddleware,
    ): Middleware<CityState, CityAction>
}
