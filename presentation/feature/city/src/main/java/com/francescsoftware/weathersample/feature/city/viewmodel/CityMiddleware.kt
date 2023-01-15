package com.francescsoftware.weathersample.feature.city.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.feature.city.R
import com.francescsoftware.weathersample.feature.city.model.CityResultModel
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

    override fun process(
        state: CityState,
        action: CityAction,
    ) {
        when (action) {
            CityAction.Start -> onStart()
            is CityAction.QueryUpdated -> onQueryUpdated(action.query)
            else -> {}
        }
    }

    private fun onStart() {
        job?.cancel()
        job = searchFlow
            .distinctUntilChanged()
            .debounce(DebounceMillis)
            .map { prefix ->
                dispatch(CityAction.Loading)
                getCitiesInteractor.execute(
                    prefix = prefix,
                )
            }.onEach { cities ->
                onCitiesLoaded(cities)
            }.launchIn(scope)
    }

    private fun onQueryUpdated(
        query: TextFieldValue,
    ) {
        if (query.text.length >= MinCityLengthForSerch) {
            scope.launch {
                searchFlow.emit(query.text)
            }
        }
    }

    private fun onCitiesLoaded(
        cities: Result<List<City>>,
    ) {
        cities.fold(
            onSuccess = { list ->
                if (list.isEmpty()) {
                    dispatch(CityAction.NoResults)
                } else {
                    dispatch(
                        CityAction.CitiesLoaded(
                            list.map { city -> city.toCityResultModel() }
                        )
                    )
                }
            },
            onFailure = {
                dispatch(CityAction.LoadError)
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
