package com.francescsoftware.weathersample.presentation.feature.weather.tabs.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.francescsoftware.weathersample.presentation.feature.databinding.FragmentForecastBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private lateinit var binding: FragmentForecastBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }
}
