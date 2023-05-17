package com.example.weatherapp.presentation.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.common.BackgroundController
import com.example.weatherapp.presentation.common.NetworkService
import com.example.weatherapp.presentation.common.WeatherCodeTranslator
import com.example.weatherapp.presentation.daily.DailyBottomSheetFragment
import com.example.weatherapp.presentation.hourly.HourlyBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel.load(NetworkService.isConnected(context))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentMainBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeListener()
        setupLoadingState()
        setupErrorState()
        setupWeatherState()
        setupErrorRefreshListeners()
    }

    private fun setupSwipeListener() {
        binding.mainLayout.setOnRefreshListener { mainViewModel.load(NetworkService.isConnected(requireContext())) }
    }

    private fun setupLoadingState() {
        lifecycleScope.launch {
            mainViewModel.isLoading.collectLatest {
                binding.mainLayout.isRefreshing = it
                binding.content.isVisible = !binding.error.isVisible
            }
        }
    }

    private fun setupErrorState() {
        lifecycleScope.launch {
            mainViewModel.isError.collectLatest {
                binding.error.isVisible = it
                if (it) BackgroundController.set(R.drawable.thunder, requireActivity(), requireContext())
            }
        }
    }

    private fun setupWeatherState() {
        lifecycleScope.launch {
            mainViewModel.weather.collectLatest {
                it?.let {
                    setupUI(it)
                }
            }
        }
    }

    private fun setupUI(weather: Weather) {
        setupHourlyListeners(weather)
        setupDailyListeners(weather)

        val backgroundRes = WeatherCodeTranslator.toBackgroundDrawableRes(weather.currentWeather?.weatherCode, weather.currentWeather?.isDay)
        val iconRes = WeatherCodeTranslator.toIconDrawableRes(weather.currentWeather?.weatherCode, weather.currentWeather?.isDay)
        val statusRes = WeatherCodeTranslator.toStatusStringRes(weather.currentWeather?.weatherCode)

        BackgroundController.set(backgroundRes, requireActivity(), requireContext())
        binding.location.text = weather.location
        binding.temperature.text = getString(R.string.temperature, weather.currentWeather?.temperature.toString())
        binding.statusIcon.setImageResource(iconRes)
        binding.status.text = getString(statusRes)
        binding.windSpeed.text = getString(R.string.wind_speed, weather.currentWeather?.windSpeed.toString())
    }

    private fun setupHourlyListeners(weather: Weather) {
        binding.buttonHourly.setOnClickListener {
            HourlyBottomSheetFragment(weather.hourly) { id ->
                findNavController().navigate(MainFragmentDirections.toHourlyDetails(id))
            }.show(requireParentFragment().parentFragmentManager, HourlyBottomSheetFragment.TAG)
        }
    }

    private fun setupDailyListeners(weather: Weather) {
        binding.buttonDaily.setOnClickListener {
            DailyBottomSheetFragment(weather.daily) { id ->
                findNavController().navigate(MainFragmentDirections.toDailyDetail(id))
            }.show(requireParentFragment().parentFragmentManager, DailyBottomSheetFragment.TAG)
        }
    }

    private fun setupErrorRefreshListeners() {
        binding.errorRefresh.setOnClickListener {
            binding.mainLayout.isRefreshing = true
            mainViewModel.load(NetworkService.isConnected(requireContext()))
        }
    }
}
