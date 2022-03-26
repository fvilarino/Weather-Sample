package com.francescsoftware.weathersample.feature.city

import androidx.lifecycle.viewModelScope
import com.francescsoftware.weathersample.feature.navigation.api.Navigator
import com.francescsoftware.weathersample.feature.navigation.api.SelectedCity
import com.francescsoftware.weathersample.interactor.city.api.City
import com.francescsoftware.weathersample.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.mvi.MviViewModel
import com.francescsoftware.weathersample.type.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private const val MIN_CITY_LENGTH_FOR_SEARCH = 3
private const val TAG = "CityViewModel"
private val DebounceMillis = 400L.toDuration(DurationUnit.MILLISECONDS)

@HiltViewModel
internal class CityViewModel @Inject constructor(
    private val getCitiesInteractor: GetCitiesInteractor,
    private val navigator: Navigator,
    private val stringLookup: StringLookup,
) : MviViewModel<CityState, CityEvent, CityMviIntent, CityReduceAction>(
    initialState = CityState.initial,
), CityCallbacks {

    private val searchFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)

    init {
        onStart()
    }

    override fun onCityClick(city: CityResultModel) {
        Timber.tag(TAG).d("Clicked on city [$city]")
        navigator.cityToWeather(city.toSelectedCity())
    }

    override fun onQueryChange(query: String) {
        onIntent(CityMviIntent.PrefixUpdated(query))
    }

    override suspend fun executeIntent(intent: CityMviIntent) {
        when (intent) {
            is CityMviIntent.PrefixUpdated -> onPrefixUpdated(intent.prefix)
            is CityMviIntent.CitiesLoaded -> onCitiesLoaded(intent.cities)
            CityMviIntent.NoResults -> handle(CityReduceAction.NoResults)
            CityMviIntent.LoadError -> handle(CityReduceAction.LoadError)
        }
    }

    override fun reduce(state: CityState, reduceAction: CityReduceAction): CityState =
        when (reduceAction) {
            CityReduceAction.Loading -> state.copy(loadState = LoadState.Loading)
            is CityReduceAction.PrefixUpdated -> state.copy(query = reduceAction.prefix)
            is CityReduceAction.Loaded -> state.copy(
                loadState = LoadState.Loaded,
                cities = reduceAction.cities,
            )
            CityReduceAction.LoadError -> state.copy(loadState = LoadState.Error)
            CityReduceAction.NoResults -> state.copy(loadState = LoadState.NoResults)
        }

    private fun onStart() {
        searchFlow
            .distinctUntilChanged()
            .debounce(DebounceMillis)
            .map { prefix -> getCitiesInteractor.execute(prefix) }
            .onEach { cities ->
                cities.fold(
                    onSuccess = { list ->
                        onIntent(CityMviIntent.CitiesLoaded(list))
                    },
                    onFailure = {
                        onIntent(CityMviIntent.LoadError)
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    private suspend fun onPrefixUpdated(prefix: String) {
        handle(CityReduceAction.PrefixUpdated(prefix))
        if (prefix.length >= MIN_CITY_LENGTH_FOR_SEARCH) {
            handle(CityReduceAction.Loading)
            searchFlow.emit(prefix)
        } else {
            handle(CityReduceAction.Loaded(emptyList()))
        }
    }

    private fun onCitiesLoaded(cities: List<City>) {
        if (cities.isNotEmpty()) {
            val cityModels = cities.map { city -> city.toCityResultModel() }
            handle(CityReduceAction.Loaded(cities = cityModels))
        } else {
            handle(CityReduceAction.NoResults)
        }
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

    private fun CityResultModel.toSelectedCity() = SelectedCity(
        name = name.toString(),
        country = country.toString(),
        countryCode = countryCode,
    )
}
