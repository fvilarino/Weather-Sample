package com.francescsoftware.weathersample.feature.weather

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.francescsoftware.weathersample.feature.common.widget.ToggleOptions
import com.francescsoftware.weathersample.feature.weather.tabs.forecast.ForecastFragment
import com.francescsoftware.weathersample.feature.weather.tabs.today.TodayFragment

enum class WeatherFragments {
    TODAY,
    FORECAST;

    companion object {
        fun fromOrdinal(ordinal: Int) = values().getOrNull(ordinal) ?: TODAY
        fun fromToggleOption(toggleOptions: ToggleOptions) = when (toggleOptions) {
            ToggleOptions.LEFT -> TODAY
            ToggleOptions.RIGHT -> FORECAST
        }
    }
}

class WeatherPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = WeatherFragments.values().size

    override fun createFragment(position: Int): Fragment =
        when (WeatherFragments.fromOrdinal(position)) {
            WeatherFragments.TODAY -> TodayFragment()
            WeatherFragments.FORECAST -> ForecastFragment()
        }
}
