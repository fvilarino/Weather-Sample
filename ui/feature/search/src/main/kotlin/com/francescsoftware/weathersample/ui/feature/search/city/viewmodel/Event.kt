package com.francescsoftware.weathersample.ui.feature.search.city.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.shared.mvi.Action
import com.francescsoftware.weathersample.ui.shared.mvi.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import javax.annotation.concurrent.Immutable

internal enum class LoadState {
    Idle,
    Loading,
    Loaded,
    NoResults,
    Error,
}

@Immutable
internal data class CityState(
    val loadState: LoadState,
    val cities: ImmutableList<CityResultModel>,
    val recentCities: ImmutableList<RecentCityModel>,
    val showRecentCities: Boolean,
) : State {
    val loading = loadState == LoadState.Loading
    val loaded = loadState == LoadState.Loaded

    companion object {
        val initial = CityState(
            loadState = LoadState.Idle,
            cities = persistentListOf(),
            recentCities = persistentListOf(),
            showRecentCities = false,
        )
    }
}

internal sealed interface CityAction : Action {
    data class QueryUpdated(val query: TextFieldValue) : CityAction
    data class CitiesLoaded(val cities: ImmutableList<CityResultModel>) : CityAction
    data class RecentCitiesLoaded(val recentCities: ImmutableList<RecentCityModel>) : CityAction
    data class OnCityClick(val cityModel: RecentCityModel) : CityAction
    data class OnFavoriteClick(val city: CityResultModel) : CityAction
    data class OnChipClick(val recentCityModel: RecentCityModel) : CityAction
    data class OnDeleteChipClick(val recentCityModel: RecentCityModel) : CityAction
    object Start : CityAction
    object Loading : CityAction
    object NoResults : CityAction
    object LoadError : CityAction
    object QueryFocused : CityAction
    object HideRecentCities : CityAction
}
