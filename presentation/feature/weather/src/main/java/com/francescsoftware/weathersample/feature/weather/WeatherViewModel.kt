package com.francescsoftware.weathersample.feature.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francescsoftware.weathersample.feature.navigation.api.NavigationDestination
import com.francescsoftware.weathersample.feature.navigation.api.SelectedCity
import com.francescsoftware.weathersample.interactor.weather.api.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.api.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.shared.mvi.ActionHandler
import com.francescsoftware.weathersample.shared.mvi.Middleware
import com.francescsoftware.weathersample.shared.mvi.Reducer
import com.francescsoftware.weathersample.shared.mvi.StateReducerFlow
import com.francescsoftware.weathersample.shared.mvi.stateReducerFlow
import com.francescsoftware.weathersample.time.api.TimeFormatter
import com.francescsoftware.weathersample.type.fold
import com.francescsoftware.weathersample.type.getOrNull
import com.francescsoftware.weathersample.utils.time.isToday
import com.francescsoftware.weathersample.utils.time.isTomorrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

internal interface WeatherCallbacks {
    fun onOptionSelect(selectedWeatherScreen: SelectedWeatherScreen)
    fun refreshTodayWeather()
    fun retry()
}

internal class WeatherMiddleware @Inject constructor(
    private val getTodayWeatherInteractor: GetTodayWeatherInteractor,
    private val getForecastInteractor: GetForecastInteractor,
    private val timeFormatter: TimeFormatter,
    private val stringLookup: StringLookup,
) : Middleware<WeatherState, WeatherAction> {

    private data class CityInfo(
        val name: String,
        val countryCode: String,
    )

    private lateinit var actionHandler: ActionHandler<WeatherAction>
    private lateinit var scope: CoroutineScope
    private var cityInfo: CityInfo? = null

    override fun setup(scope: CoroutineScope, actionHandler: ActionHandler<WeatherAction>) {
        this.actionHandler = actionHandler
        this.scope = scope
    }

    override fun reduce(
        state: WeatherState,
        action: WeatherAction,
    ): WeatherState = when (action) {
        WeatherAction.Retry -> {
            cityInfo?.let { info ->
                scope.launch { load(info) }
            } ?: throw IllegalStateException("Retry called out of sequence")
            state.copy(
                loadState = WeatherLoadState.Loading,
            )
        }
        is WeatherAction.Load -> {
            cityInfo = CityInfo(
                name = action.city,
                countryCode = action.countryCode,
            ).also { info ->
                scope.launch { load(info) }
            }
            state.copy(
                loadState = WeatherLoadState.Loading,
            )
        }
        WeatherAction.RefreshTodayWeather -> {
            cityInfo?.let { info ->
                scope.launch { loadTodayWeather(info) }
            } ?: throw IllegalStateException("Retry called out of sequence")
            state.copy(
                loadState = WeatherLoadState.Loading,
            )
        }
        else -> state
    }

    private suspend fun load(cityInfo: CityInfo) = coroutineScope {
        val location = WeatherLocation.City(
            name = cityInfo.name,
            countryCode = cityInfo.countryCode,
        )
        val currentAsync = async { getTodayWeatherInteractor.execute(location) }
        val forecastAsync = async { getForecastInteractor.execute(location) }
        val current = currentAsync.await().getOrNull()
        val forecast = forecastAsync.await().getOrNull()
        if (current != null && forecast != null) {
            actionHandler.handleAction(
                WeatherAction.Loaded(
                    currentWeather = current.toWeatherCardState(),
                    forecastItems = forecast.toForecastItems()
                )
            )
        } else {
            actionHandler.handleAction(
                WeatherAction.LoadError(
                    message = stringLookup.getString(R.string.failed_to_load_weather_data)
                )
            )
        }
    }

    private suspend fun loadTodayWeather(cityInfo: CityInfo) {
        val location = WeatherLocation.City(
            name = cityInfo.name,
            countryCode = cityInfo.countryCode,
        )
        getTodayWeatherInteractor.execute(location).fold(
            onSuccess = { todayWeather ->
                actionHandler.handleAction(
                    WeatherAction.TodayLoaded(
                        currentWeather = todayWeather.toWeatherCardState(),
                    )
                )
            },
            onFailure = {
                actionHandler.handleAction(
                    WeatherAction.LoadError(
                        message = stringLookup.getString(R.string.failed_to_load_weather_data)
                    )
                )
            }
        )
    }

    private fun Forecast.toForecastItems(): List<ForecastItem> = items.map { forecastDay ->
        val header = forecastDay.toForecastHeaderState()
        val content = forecastDay.entries.map { entry -> entry.toForecastCardState() }
        listOf(header) + content
    }.flatten()

    private fun TodayWeather.toWeatherCardState() =
        TodayWeatherCardState(
            temperature = main.temp.formatTemperature(stringLookup),
            minTemperature = main.tempMin.formatTemperature(stringLookup),
            maxTemperature = main.tempMax.formatTemperature(stringLookup),
            feelsLikeTemperature = main.feelsLike.formatTemperature(stringLookup),
            description = formatDescription(),
            windSpeed = wind.speed.formatWind(stringLookup),
            humidity = main.humidity.formatHumidity(stringLookup),
            pressure = main.pressure.formatPressure(stringLookup),
            visibility = visibility.formatVisibility(stringLookup),
            iconId = icon,
        )

    private fun ForecastDay.toForecastHeaderState(): ForecastItem.ForecastHeader =
        ForecastItem.ForecastHeader(
            id = date.time,
            date = date.toHeaderLabel(),
            sunrise = timeFormatter.formatHour(sunrise),
            sunset = timeFormatter.formatHour(sunset),
        )

    private fun Date.toHeaderLabel(): String = when {
        isToday -> stringLookup.getString(R.string.today)
        isTomorrow -> stringLookup.getString(R.string.tomorrow)
        else -> timeFormatter.formatDayWithDayOfWeek(this)
    }

    private fun ForecastEntry.toForecastCardState(): ForecastItem.ForecastCard =
        ForecastItem.ForecastCard(
            id = date.time,
            header = stringLookup.getString(
                R.string.forecast_card_header,
                timeFormatter.formatHour(date),
                description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            ),
            iconId = WeatherIcon.fromIconId(icon).iconId,
            minTemperature = minTemperature.formatTemperature(stringLookup),
            maxTemperature = maxTemperature.formatTemperature(stringLookup),
            feelsLikeTemperature = feelsLikeTemperature.formatTemperature(stringLookup),
            windSpeed = windSpeed.formatWind(stringLookup),
            humidity = humidityPercent.formatHumidity(stringLookup),
            visibility = visibility.formatVisibility(stringLookup),
        )

    private val TodayWeather.icon: Int
        get() = WeatherIcon.fromIconId(weather.icon).iconId
}

internal class WeatherReducer @Inject constructor() : Reducer<WeatherState, WeatherAction> {
    override fun reduce(
        state: WeatherState,
        action: WeatherAction,
    ): WeatherState = when (action) {
        is WeatherAction.CityUpdated -> state.copy(
            cityName = action.name,
            cityCountryCode = action.countryCode,
        )
        is WeatherAction.Loaded -> state.copy(
            loadState = WeatherLoadState.Loaded,
            todayState = action.currentWeather,
            forecastItems = action.forecastItems,
        )
        is WeatherAction.TodayLoaded -> state.copy(
            loadState = WeatherLoadState.Loaded,
            todayState = action.currentWeather,
        )
        is WeatherAction.LoadError -> state.copy(
            loadState = WeatherLoadState.Error,
            errorMessage = action.message,
        )
        is WeatherAction.OnOptionSelected -> state.copy(
            option = action.option,
        )
        else -> state
    }
}

@HiltViewModel
internal class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    reducer: WeatherReducer,
    middleware: WeatherMiddleware,
) : ViewModel(), WeatherCallbacks {

    private val stateReducer: StateReducerFlow<WeatherState, WeatherAction> = stateReducerFlow(
        initialState = WeatherState.initial,
        reducer = reducer,
        middleware = listOf(middleware),
    )

    val state: StateFlow<WeatherState> = stateReducer

    private val selectedCity: SelectedCity = NavigationDestination.Weather.getCity(savedStateHandle)

    init {
        middleware.setup(
            scope = viewModelScope,
            actionHandler = stateReducer,
        )
        stateReducer.handleAction(
            WeatherAction.CityUpdated(
                name = selectedCity.name,
                countryCode = selectedCity.countryCode
            )
        )
        stateReducer.handleAction(
            WeatherAction.Load(
                city = selectedCity.name,
                countryCode = selectedCity.countryCode,
            )
        )
    }

    override fun onOptionSelect(selectedWeatherScreen: SelectedWeatherScreen) {
        stateReducer.handleAction(WeatherAction.OnOptionSelected(selectedWeatherScreen))
    }

    override fun refreshTodayWeather() {
        stateReducer.handleAction(WeatherAction.RefreshTodayWeather)
    }

    override fun retry() {
        stateReducer.handleAction(WeatherAction.Retry)
    }
}
