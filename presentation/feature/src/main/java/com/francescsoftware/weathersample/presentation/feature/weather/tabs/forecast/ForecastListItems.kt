package com.francescsoftware.weathersample.presentation.feature.weather.tabs.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.databinding.ViewholderForecastCardBinding
import com.francescsoftware.weathersample.presentation.feature.databinding.ViewholderForecastHeaderBinding
import com.francescsoftware.weathersample.presentation.shared.recyclerview.Diffable
import com.francescsoftware.weathersample.presentation.shared.recyclerview.RecyclerViewBindingItem
import com.francescsoftware.weathersample.presentation.shared.recyclerview.RecyclerViewViewHolder

class ForecastHeaderViewHolder(
    binding: ViewholderForecastHeaderBinding
) : RecyclerViewViewHolder<ForecastHeaderBindingItem>(binding) {

    override fun bind(bindingItem: ForecastHeaderBindingItem) {
        (binding as ViewholderForecastHeaderBinding).state = bindingItem.forecastHeaderState
        binding.executePendingBindings()
    }
}

class ForecastCardViewHolder(
    binding: ViewholderForecastCardBinding
) : RecyclerViewViewHolder<ForecastCardBindingItem>(binding) {

    override fun bind(bindingItem: ForecastCardBindingItem) {
        (binding as ViewholderForecastCardBinding).state = bindingItem.forecastCardState
        binding.executePendingBindings()
    }
}

class ForecastHeaderBindingItem(
    val forecastHeaderState: ForecastHeaderState
) : RecyclerViewBindingItem, Diffable by forecastHeaderState {

    override val type: Int = R.layout.viewholder_forecast_header

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder<*> {
        val binding = ViewholderForecastHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastHeaderViewHolder(binding)
    }
}

class ForecastCardBindingItem(
    val forecastCardState: ForecastCardState
) : RecyclerViewBindingItem, Diffable by forecastCardState {

    override val type: Int = R.layout.viewholder_forecast_card

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder<*> {
        val binding = ViewholderForecastCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastCardViewHolder(binding)
    }
}
