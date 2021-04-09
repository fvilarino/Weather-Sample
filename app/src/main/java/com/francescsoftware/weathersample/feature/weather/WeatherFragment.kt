package com.francescsoftware.weathersample.feature.weather

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.francescsoftware.weathersample.databinding.FragmentWeatherBinding
import com.francescsoftware.weathersample.feature.common.mvi.MviLifecycleObserver
import com.francescsoftware.weathersample.feature.common.mvi.MviLifecycleView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedCity(
    val name: String,
    val country: String,
    val countryCode: String,
) : Parcelable

@AndroidEntryPoint
class WeatherFragment : Fragment(), MviLifecycleView<WeatherState, WeatherEvent> {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var mviLifecycleObserver: MviLifecycleObserver<WeatherState, WeatherEvent, WeatherIntent>
    private val viewModel: WeatherViewModel by viewModels()
    private val args: WeatherFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        mviLifecycleObserver = MviLifecycleObserver(this, viewModel).also {
            lifecycle.addObserver(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)
        viewModel.onIntent(WeatherIntent.CityParsed(args.city))
        binding.weatherPager.adapter = WeatherPagerAdapter(this)
        binding.weatherToggle.setCallback { options ->
            binding.weatherPager.currentItem = WeatherFragments.fromToggleOption(options).ordinal
        }
    }

    override fun onState(state: WeatherState) {
        binding.state = state
    }
}
