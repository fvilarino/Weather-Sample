package com.francescsoftware.weathersample.presentation.feature.search

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.francescsoftware.weathersample.interactor.city.City
import com.francescsoftware.weathersample.interactor.city.GetCitiesInteractor
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.navigator.Navigator
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import com.francescsoftware.weathersample.presentation.shared.lookup.StringLookup
import com.francescsoftware.weathersample.presentation.shared.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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
    private val stringLookup: StringLookup,
) :
    MviViewModel<CityState, CityEvent, CityMviIntent, CityReduceAction>(
        initialState = CityState.initial
    ), DefaultLifecycleObserver {

    private val searchFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    private var searchJob: Job? = null

    private val cityClickCallback = { city: CityResultModel ->
        Timber.tag(TAG).d("Clicked on city [$city]")
        navigator.cityToWeather(
            SelectedCity(
                name = city.name.toString(),
                country = city.country.toString(),
                countryCode = city.countryCode,
            )
        )
    }

    override fun onStart(owner: LifecycleOwner) {
        searchJob = viewModelScope.launch {
            searchFlow
                .distinctUntilChanged()
                .debounce(DebounceMillis)
                .map { prefix -> getCitiesInteractor.execute(prefix) }
                .collectLatest { cities ->
                    cities.fold(
                        onSuccess = { list ->
                            if (list.isNotEmpty()) {
                                val cityModels = list.map { city -> city.toCityResultModel() }
                                handle(CityReduceAction.Loaded(cities = cityModels))
                            } else {
                                handle(CityReduceAction.NoResults)
                            }
                        },
                        onFailure = {
                            handle(CityReduceAction.LoadError)
                            onEvent(
                                CityEvent.ShowSnackBar(
                                    stringLookup.getString(R.string.city_error_loading)
                                )
                            )
                        }
                    )
                }
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        searchJob?.cancel()
        searchJob = null
    }

    override suspend fun executeIntent(intent: CityMviIntent) {
        when (intent) {
            is CityMviIntent.PrefixUpdated -> {
                if (intent.prefix.length >= MIN_CITY_LENGTH_FOR_SEARCH) {
                    handle(CityReduceAction.Loading)
                    searchFlow.emit(intent.prefix)
                } else {
                    handle(CityReduceAction.Loaded(emptyList()))
                }
            }
        }
    }

    override fun reduce(state: CityState, reduceAction: CityReduceAction): CityState =
        when (reduceAction) {
            CityReduceAction.Loading -> state.copy(loadState = LoadState.LOADING)
            is CityReduceAction.Loaded -> state.copy(
                loadState = LoadState.LOADED,
                cities = reduceAction.cities,
            )
            CityReduceAction.LoadError -> state.copy(loadState = LoadState.ERROR)
            CityReduceAction.NoResults -> state.copy(loadState = LoadState.NO_RESULTS)
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
    ).apply {
        clickCallback = cityClickCallback
    }
}
