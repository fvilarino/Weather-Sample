package com.francescsoftware.weathersample.ui.feature.search.weather.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import com.francescsoftware.weathersample.core.time.api.isToday
import com.francescsoftware.weathersample.core.time.api.isTomorrow
import com.francescsoftware.weathersample.core.type.either.valueOrNull
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastDay
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastEntry
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayMain
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayWeather
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastDate
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.francescsoftware.weathersample.ui.shared.weathericon.drawableId
import com.francescsoftware.weathersample.ui.shared.weathericon.weatherIconFromCode
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import java.time.ZonedDateTime
import java.util.Locale


private sealed class LoadTrigger {
    abstract val index: Int

    data class Refresh(override val index: Int) : LoadTrigger()
    data class Retry(override val index: Int) : LoadTrigger()
}

class WeatherPresenter @AssistedInject constructor(
    @Assisted private val screen: WeatherScreen,
    private val getTodayWeatherInteractor: GetTodayWeatherInteractor,
    private val getForecastInteractor: GetForecastInteractor,
    private val timeFormatter: TimeFormatter,
) : Presenter<WeatherScreen.State> {

    @CircuitInject(WeatherScreen::class, ActivityScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(
            screen: WeatherScreen,
        ): WeatherPresenter
    }

    @Composable
    override fun present(): WeatherScreen.State {
        val scope = rememberCoroutineScope()
        val location = remember {
            WeatherLocation.City(
                name = screen.selectedCity.name,
                countryCode = screen.selectedCity.countryCode,
            )
        }
        var triggerIndex by remember { mutableIntStateOf(0) }
        var refreshTrigger: LoadTrigger by remember { mutableStateOf(LoadTrigger.Retry(triggerIndex)) }
        var refreshing by remember { mutableStateOf(false) }
        val weatherFlow = rememberRetained(
            inputs = arrayOf(getTodayWeatherInteractor, getForecastInteractor),
        ) {
            snapshotFlow { refreshTrigger }
                .flatMapLatest { trigger ->
                    flow {
                        when(trigger) {
                            is LoadTrigger.Refresh -> refreshing = true
                            is LoadTrigger.Retry -> emit(WeatherScreen.Weather.Loading)
                        }
                        emit(loadWeather(location))
                        refreshing = false
                    }
                }
                .shareIn(scope = scope, started = SharingStarted.Lazily)
        }
        val weather by weatherFlow.collectAsRetainedState(initial = WeatherScreen.Weather.Loading)
        fun eventSink(event: WeatherScreen.Event) {
            when (event) {
                WeatherScreen.Event.RefreshClick -> refreshTrigger = LoadTrigger.Refresh(triggerIndex++)
                WeatherScreen.Event.RetryClick -> refreshTrigger = LoadTrigger.Retry(triggerIndex++)
            }
        }
        return WeatherScreen.State(
            weather = weather,
            refreshing = refreshing,
            eventSink = ::eventSink,
        )
    }

    private suspend fun loadWeather(location: WeatherLocation.City) = coroutineScope {
        val currentAsync = async { getTodayWeatherInteractor(location) }
        val forecastAsync = async { getForecastInteractor(location) }
        val current = currentAsync.await().valueOrNull()
        val forecast = forecastAsync.await().valueOrNull()
        if (current != null && forecast != null) {
            WeatherScreen.Weather.Loaded(
                cityName = screen.selectedCity.name,
                cityCountryCode = screen.selectedCity.countryCode,
                current.toWeatherCardState(),
                forecast.toForecastItems(),
            )
        } else {
            WeatherScreen.Weather.Error
        }
    }

    private fun TodayWeather.toWeatherCardState() =
        CurrentWeatherState(
            temperature = main.temperature,
            feelsLikeTemperature = main.feelsLike,
            precipitation = main.precipitation,
            uvIndex = main.uvIndex,
            description = main.formatDescription(),
            windSpeed = wind.speed,
            gustSpeed = wind.gust,
            humidity = main.humidity,
            pressure = main.pressure,
            visibility = visibility,
            iconId = icon,
        )

    private val TodayWeather.icon: Int
        get() = weatherIconFromCode(main.code).drawableId

    private fun Forecast.toForecastItems(): ImmutableList<WeatherScreen.ForecastDayState> = items.map { forecastDay ->
        WeatherScreen.ForecastDayState(
            header = forecastDay.toForecastHeaderState(),
            forecast = forecastDay
                .entries.map { entry ->
                    entry.toForecastCardState()
                }
                .toImmutableList(),
        )
    }.toImmutableList()

    private fun ForecastDay.toForecastHeaderState(): ForecastHeaderState =
        ForecastHeaderState(
            id = "header_$date",
            date = date.toHeaderLabel(),
            sunrise = sunrise,
            sunset = sunset,
        )

    private fun ZonedDateTime.toHeaderLabel(): ForecastDate = when {
        isToday -> ForecastDate.Today
        isTomorrow -> ForecastDate.Tomorrow
        else -> ForecastDate.Day(timeFormatter.formatDayWithDayOfWeek(this))
    }

    private fun ForecastEntry.toForecastCardState(): ForecastHourState =
        ForecastHourState(
            id = zonedDateTime.toEpochSecond(),
            time = timeFormatter.formatHour(zonedDateTime),
            description = description.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            },
            iconId = weatherIconFromCode(iconCode).drawableId,
            temperature = temperature,
            feelsLikeTemperature = feelsLike,
            precipitation = precipitation,
            uvIndex = uvIndex,
            windSpeed = windSpeed,
            humidity = humidity,
            visibility = visibility,
        )

    private fun TodayMain.formatDescription(): String =
        description.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
}
