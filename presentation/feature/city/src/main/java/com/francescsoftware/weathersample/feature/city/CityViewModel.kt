package com.francescsoftware.weathersample.feature.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francescsoftware.weathersample.feature.navigation.api.Navigator
import com.francescsoftware.weathersample.feature.navigation.api.SelectedCity
import com.francescsoftware.weathersample.interactor.city.api.City
import com.francescsoftware.weathersample.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.shared.mvi.ActionHandler
import com.francescsoftware.weathersample.shared.mvi.Middleware
import com.francescsoftware.weathersample.shared.mvi.Reducer
import com.francescsoftware.weathersample.shared.mvi.StateReducerFlow
import com.francescsoftware.weathersample.shared.mvi.stateReducerFlow
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private const val MIN_CITY_LENGTH_FOR_SEARCH = 3
private const val TAG = "CityViewModel"
private val DebounceMillis = 400L.toDuration(DurationUnit.MILLISECONDS)

internal class CityMiddleware @Inject constructor(
    private val getCitiesInteractor: GetCitiesInteractor,
    private val stringLookup: StringLookup,
) : Middleware<CityState, CityAction> {

    private val searchFlow = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    private var job: Job? = null
    private lateinit var actionHandler: ActionHandler<CityAction>
    private lateinit var scope: CoroutineScope

    override fun setup(scope: CoroutineScope, actionHandler: ActionHandler<CityAction>) {
        this.actionHandler = actionHandler
        this.scope = scope
        job?.cancel()
        job = searchFlow
            .distinctUntilChanged()
            .debounce(DebounceMillis)
            .map { prefix ->
                actionHandler.handleAction(CityAction.Loading)
                getCitiesInteractor.execute(prefix)
            }.onEach { cities ->
                onCitiesLoaded(cities)
            }.launchIn(scope)
    }

    override fun reduce(
        state: CityState,
        action: CityAction,
    ): CityState = when (action) {
        is CityAction.PrefixUpdated -> onPrefixUpdated(state, action.prefix)
        else -> state
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

internal class CityReducer @Inject constructor() : Reducer<CityState, CityAction> {

    override fun reduce(
        state: CityState,
        action: CityAction,
    ): CityState = when (action) {
        is CityAction.CitiesLoaded -> state.copy(
            loadState = LoadState.Loaded,
            cities = action.cities,
        )
        CityAction.LoadError -> state.copy(loadState = LoadState.Error)
        CityAction.NoResults -> state.copy(loadState = LoadState.NoResults)
        CityAction.Loading -> state.copy(loadState = LoadState.Loading)
        else -> state
    }
}

@HiltViewModel
internal class CityViewModel @Inject constructor(
    reducer: CityReducer,
    middleware: CityMiddleware,
    private val navigator: Navigator,
) : ViewModel(), CityCallbacks {

    private val stateReducer: StateReducerFlow<CityState, CityAction> = stateReducerFlow(
        initialState = CityState.initial,
        reducer = reducer,
        middleware = listOf(middleware),
    )

    val state: StateFlow<CityState> = stateReducer

    init {
        middleware.setup(
            scope = viewModelScope,
            actionHandler = stateReducer,
        )
    }

    override fun onCityClick(city: CityResultModel) {
        Timber.tag(TAG).d("Clicked on city [$city]")
        navigator.cityToWeather(city.toSelectedCity())
    }

    override fun onQueryChange(query: String) {
        stateReducer.handleAction(CityAction.PrefixUpdated(query))
    }

    private fun CityResultModel.toSelectedCity() = SelectedCity(
        name = name.toString(),
        country = country.toString(),
        countryCode = countryCode,
    )
}
