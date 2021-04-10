package com.francescsoftware.weathersample.presentation.feature.weather.tabs.today

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import com.francescsoftware.weathersample.presentation.feature.databinding.TodayCardViewBinding
import com.google.android.material.card.MaterialCardView

data class TodayWeatherCardState(
    val temperature: CharSequence = "",
    val minTemperature: CharSequence = "",
    val maxTemperature: CharSequence = "",
    val feelsLikeTemperature: CharSequence = "",
    val description: CharSequence = "",
    @DrawableRes val iconId: Int = 0,
    val windSpeed: CharSequence = "",
    val humidity: CharSequence = "",
    val pressure: CharSequence = "",
    val visibility: CharSequence = "",
)

class TodayWeatherCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding = TodayCardViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setState(state: TodayWeatherCardState?) {
        if (state != null) {
            binding.state = state
        }
    }
}
