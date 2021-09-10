package com.francescsoftware.weathersample.presentation.feature.search

import androidx.lifecycle.viewModelScope
import com.francescsoftware.weathersample.interactor.city.City
import com.francescsoftware.weathersample.interactor.city.GetCitiesInteractor
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.navigator.Navigator
import com.francescsoftware.weathersample.presentation.shared.lookup.StringLookup
import com.francescsoftware.weathersample.presentation.shared.mvi.MviViewModel
import com.francescsoftware.weathersample.storage.selectedcity.SelectedCityStore
import com.francescsoftware.weathersample.type.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
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

@HiltViewModel
class CityViewModel @Inject constructor(
    private val getCitiesInteractor: GetCitiesInteractor,
    private val navigator: Navigator,
    private val cityStore: SelectedCityStore,
    private val stringLookup: StringLookup,
) : MviViewModel<CityState, CityEvent, CityMviIntent, CityReduceAction>(
    initialState = CityState.initial
), CityCallbacks {

    private val searchFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)

    init {
        onStart()
    }

    override fun onCityClick(city: CityResultModel) {
        Timber.tag(TAG).d("Clicked on city [$city]")
        viewModelScope.launch {
            cityStore.setSelectedCity(city.name.toString(), city.country.toString(), city.countryCode)
            navigator.cityToWeather()
        }
    }

    override fun onQueryChange(query: String) {
        onIntent(CityMviIntent.PrefixUpdated(query))
    }

    override suspend fun executeIntent(intent: CityMviIntent) {
        when (intent) {
            is CityMviIntent.PrefixUpdated -> {
                handle(CityReduceAction.PrefixUpdated(intent.prefix))
                if (intent.prefix.length >= MIN_CITY_LENGTH_FOR_SEARCH) {
                    handle(CityReduceAction.Loading)
                    searchFlow.emit(intent.prefix)
                } else {
                    handle(CityReduceAction.Loaded(emptyList()))
                }
            }
            is CityMviIntent.CitiesLoaded -> handle(CityReduceAction.Loaded(cities = intent.cities))
            CityMviIntent.NoResults -> handle(CityReduceAction.NoResults)
            CityMviIntent.LoadError -> handle(CityReduceAction.LoadError)
        }
    }

    override fun reduce(state: CityState, reduceAction: CityReduceAction): CityState =
        when (reduceAction) {
            CityReduceAction.Loading -> state.copy(loadState = LoadState.LOADING)
            is CityReduceAction.PrefixUpdated -> state.copy(query = reduceAction.prefix)
            is CityReduceAction.Loaded -> state.copy(
                loadState = LoadState.LOADED,
                cities = reduceAction.cities,
            )
            CityReduceAction.LoadError -> state.copy(loadState = LoadState.ERROR)
            CityReduceAction.NoResults -> state.copy(loadState = LoadState.NO_RESULTS)
        }

    private fun onStart() {
        searchFlow
            .distinctUntilChanged()
            .debounce(DebounceMillis)
            .map { prefix -> getCitiesInteractor.execute(prefix) }
            .onEach { cities ->
                cities.fold(
                    onSuccess = { list ->
                        if (list.isNotEmpty()) {
                            val cityModels = list.map { city -> city.toCityResultModel() }
                            onIntent(CityMviIntent.CitiesLoaded(cityModels))
                        } else {
                            onIntent(CityMviIntent.NoResults)
                        }
                    },
                    onFailure = {
                        onIntent(CityMviIntent.LoadError)
                    }
                )
            }
            .launchIn(viewModelScope)
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
        )
    )
}
