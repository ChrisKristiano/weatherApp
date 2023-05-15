package com.example.weatherapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.common.Background
import com.example.weatherapp.presentation.common.WeatherCodeTranslator
import com.example.weatherapp.presentation.daily.DailyBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val background by lazy { Background(requireActivity(), requireContext()) }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentMainBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectState()
    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.data.collectLatest {
                it?.let {
                    setupUI(it)
                }
            }
        }
    }

    private fun setupUI(weather: Weather) {
        setupDailyListeners(weather)

        val backgroundRes = WeatherCodeTranslator.toBackgroundDrawableRes(weather.currentWeather?.weatherCode, weather.currentWeather?.isDay)
        val iconRes = WeatherCodeTranslator.toIconDrawableRes(weather.currentWeather?.weatherCode, weather.currentWeather?.isDay)
        val statusRes = WeatherCodeTranslator.toStatusStringRes(weather.currentWeather?.weatherCode)

        background.set(backgroundRes)
        binding.location.text = weather.location
        binding.temperature.text = getString(R.string.temperature, weather.currentWeather?.temperature.toString())
        binding.statusIcon.setImageResource(iconRes)
        binding.status.text = getString(statusRes)
        binding.windSpeed.text = getString(R.string.wind_speed, weather.currentWeather?.windSpeed.toString())
        binding.content.isVisible = true
    }

    private fun setupDailyListeners(weather: Weather) {
        binding.buttonDaily.setOnClickListener {
            DailyBottomSheetFragment(weather.daily).show(requireParentFragment().parentFragmentManager, DailyBottomSheetFragment.TAG)
        }
    }
}
