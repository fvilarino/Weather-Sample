package com.francescsoftware.weathersample.presentation.feature.weather

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedCity(
    val name: String,
    val country: String,
    val countryCode: String,
) : Parcelable

interface SelectedCityProvider {
    val selectedCity: SelectedCity
}

@AndroidEntryPoint
class WeatherFragment : Fragment(), SelectedCityProvider {

    override lateinit var selectedCity: SelectedCity

    private lateinit var binding: FragmentWeatherBinding
    private val args: WeatherFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        selectedCity = args.city
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cityName.text = getString(
            R.string.weather_city_name,
            selectedCity.name,
            selectedCity.countryCode
        )
        binding.weatherPager.adapter = WeatherPagerAdapter(this)
        binding.weatherToggle.setCallback { options ->
            binding.weatherPager.currentItem = WeatherFragments.fromToggleOption(options).ordinal
        }
    }
}
