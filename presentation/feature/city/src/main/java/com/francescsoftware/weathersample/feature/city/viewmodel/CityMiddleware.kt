package com.francescsoftware.weathersample.feature.city.viewmodel

import com.francescsoftware.weathersample.feature.city.CityAction
import com.francescsoftware.weathersample.feature.city.CityResultModel
import com.francescsoftware.weathersample.feature.city.CityState
import com.francescsoftware.weathersample.feature.city.LoadState
import com.francescsoftware.weathersample.feature.city.R
import com.francescsoftware.weathersample.interactor.city.api.City
import com.francescsoftware.weathersample.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.shared.mvi.Middleware
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.fold
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private const val MIN_CITY_LENGTH_FOR_SEARCH = 3
private val DebounceMillis = 400L.toDuration(DurationUnit.MILLISECONDS)

internal class CityMiddleware @Inject constructor(
    private val getCitiesInteractor: GetCitiesInteractor,
    private val stringLookup: StringLookup,
) : Middleware<CityState, CityAction>() {

    private val searchFlow = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    private var job: Job? = null

    override fun reduce(
        state: CityState,
        action: CityAction,
    ): CityState = when (action) {
        CityAction.Start -> onStart(state)
        is CityAction.PrefixUpdated -> onPrefixUpdated(state, action.prefix)
        CityAction.ClearQuery -> onPrefixUpdated(state, "")
        else -> state
    }

    private fun onStart(state: CityState): CityState {
        job?.cancel()
        job = searchFlow
            .distinctUntilChanged()
            .debounce(DebounceMillis)
            .map { prefix ->
                actionHandler.handleAction(CityAction.Loading)
                getCitiesInteractor.execute(
                    prefix = prefix,
                )
            }.onEach { cities ->
                onCitiesLoaded(cities)
            }.launchIn(scope)

        return state
    }

    private fun onPrefixUpdated(
        state: CityState,
        prefix: String,
    ): CityState {
        if (prefix.length >= MIN_CITY_LENGTH_FOR_SEARCH) {
            scope.launch {
                searchFlow.emit(prefix)
            }
        }
        return state.copy(
            query = prefix,
            loadState = if (prefix.isEmpty()) LoadState.Idle else state.loadState,
        )
    }

    private fun onCitiesLoaded(
        cities: Result<List<City>>,
    ) {
        cities.fold(
            onSuccess = { list ->
                if (list.isEmpty()) {
                    actionHandler.handleAction(CityAction.NoResults)
                } else {
                    actionHandler.handleAction(
                        CityAction.CitiesLoaded(
                            list.map { city -> city.toCityResultModel() }
                        )
                    )
                }
            },
            onFailure = {
                actionHandler.handleAction(CityAction.LoadError)
            }
        )
    }

    private fun City.toCityResultModel() = CityResultModel(
        id = id.toLong(),
        name = name,
        country = country,
        countryCode = countryCode,
        coordinates = stringLookup.getString(
            R.string.coordinates_lat_lon,
            coordinates.latitude.toFloat(),
            coordinates.longitude.toFloat(),
        ),
    )
}