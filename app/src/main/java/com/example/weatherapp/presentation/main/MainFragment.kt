package com.example.weatherapp.presentation.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.common.BackgroundController
import com.example.weatherapp.presentation.common.WeatherCodeTranslator
import com.example.weatherapp.presentation.daily.DailyBottomSheetFragment
import com.example.weatherapp.presentation.hourly.HourlyBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) mainViewModel.load()
        }.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentMainBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeListener()
        setupGrantPermissionListener()
        collectErrorState()
        collectLoadingState()
        collectWeatherState()
    }

    private fun setupSwipeListener() {
        binding.mainLayout.setOnRefreshListener { mainViewModel.load() }
    }

    private fun setupGrantPermissionListener() {
        binding.errorPermission.setOnClickListener {
            startActivity(Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", requireActivity().packageName, null)
            ))
        }
    }

    private fun collectErrorState() {
        setupErrorRefreshListeners()
        lifecycleScope.launch {
            mainViewModel.error.collectLatest {
                binding.error.isVisible = it.isError
                binding.content.isVisible = !it.isError
                binding.mainLayout.isRefreshing = false
                binding.errorPermission.isVisible = it.doShowPermissionButton
                it.message?.let { binding.errorText.text = getString(it) }
                it.messageTitle?.let { binding.errorTextHeader.text = getString(it) }
                if (it.isError) BackgroundController.set(R.drawable.thunder, requireActivity(), requireContext())
            }
        }
    }

    private fun collectLoadingState() {
        lifecycleScope.launch {
            mainViewModel.isLoading.collectLatest {
                binding.mainLayout.isRefreshing = it
                binding.content.isVisible = !binding.error.isVisible &&
                        mainViewModel.weather.value != null
            }
        }
    }

    private fun collectWeatherState() {
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

        val now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0)
        val currentHour = weather.hourly.find { it.time?.isEqual(now) ?: false }
        val backgroundRes = WeatherCodeTranslator.toBackgroundDrawableRes(currentHour?.weatherCode, currentHour?.isDay)
        val iconRes = WeatherCodeTranslator.toIconDrawableRes(currentHour?.weatherCode, currentHour?.isDay)
        val statusRes = WeatherCodeTranslator.toStatusStringRes(currentHour?.weatherCode)

        BackgroundController.set(backgroundRes, requireActivity(), requireContext())
        binding.location.text = weather.location
        binding.temperature.text = getString(R.string.temperature, currentHour?.temperature.toString())
        binding.statusIcon.setImageResource(iconRes)
        binding.status.text = getString(statusRes)
        binding.feelsLike.text = getString(R.string.feels_like, currentHour?.apparentTemperature.toString())
        binding.windSpeed.text = getString(R.string.wind_speed, currentHour?.windSpeed.toString())
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
            mainViewModel.load()
        }
    }
}
