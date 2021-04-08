package com.francescsoftware.weathersample.feature.search

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.francescsoftware.weathersample.R
import com.francescsoftware.weathersample.business.interactor.city.City
import com.francescsoftware.weathersample.business.interactor.city.GetCitiesInteractor
import com.francescsoftware.weathersample.feature.common.lookup.StringLookup
import com.francescsoftware.weathersample.feature.common.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
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
    private val stringLookup: StringLookup,
) :
    MviViewModel<CityState, CityEvent, CityMviIntent, CityReduceAction>(
        initialState = CityState.initial
    ), DefaultLifecycleObserver {

    private val searchFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    private var searchJob: Job? = null

    private val cityClickCallback = { city: CityResultModel ->
        Timber.tag(TAG).d("Clicked on city [$city]")
    }

    override fun onStart(owner: LifecycleOwner) {
        searchJob = viewModelScope.launch {
            searchFlow
                .debounce(DebounceMillis)
                .map { prefix -> getCitiesInteractor.execute(prefix) }
                .collectLatest { cities ->
                    cities.fold(
                        onSuccess = { list ->
                            val cityModels = list.map { city -> city.toCityResultModel() }
                            handle(CityReduceAction.Loaded(cities = cityModels))
                        },
                        onFailure = { handle(CityReduceAction.LoadError) }
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
            CityReduceAction.Loading -> state.copy(loading = true)
            is CityReduceAction.Loaded -> state.copy(
                loading = false,
                cities = reduceAction.cities,
            )
            CityReduceAction.LoadError -> state.copy(loading = false)
        }

    private fun City.toCityResultModel() = CityResultModel(
        id = id,
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
