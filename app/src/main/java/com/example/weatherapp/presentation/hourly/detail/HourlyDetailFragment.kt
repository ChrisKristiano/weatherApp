package com.example.weatherapp.presentation.hourly.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHourlyDetailBinding
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.presentation.common.BackgroundController
import com.example.weatherapp.presentation.common.WeatherCodeTranslator
import com.example.weatherapp.presentation.main.MainViewModel
import com.example.weatherapp.presentation.util.extensions.orDash

class HourlyDetailFragment : Fragment(R.layout.fragment_hourly_detail) {

    private lateinit var binding: FragmentHourlyDetailBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: HourlyDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentHourlyDetailBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getHourlyById(args.hourlyId)?.let { setupUI(it) }
    }

    private fun setupUI(data: Hourly) {
        val newBgDrawable = WeatherCodeTranslator.toBackgroundDrawableRes(data.weatherCode, data.isDay)
        BackgroundController.set(newBgDrawable, requireActivity(), requireContext())

        binding.temperatureValue.text = getString(R.string.temperature, data.temperature.toString().orDash())
        binding.statusIcon.setImageResource(WeatherCodeTranslator.toIconDrawableRes(data.weatherCode, data.isDay))
        binding.feelsLikeValue.text = getString(R.string.temperature, data.apparentTemperature.toString().orDash())
        binding.windSpeedValue.text = getString(R.string.wind_speed_no_text, data.apparentTemperature.toString().orDash())
        binding.humidityValue.text = getString(R.string.percentage, data.humidity.toString().orDash())
        binding.cloudCoverValue.text = getString(R.string.percentage, data.humidity.toString().orDash())
        binding.rainChanceValue.text = getString(R.string.percentage, data.precipitationProbability.toString().orDash())
        binding.visibilityValue.text = getString(R.string.meters, data.visibility.toString().orDash())
        binding.pressureValue.text = getString(R.string.hPa, data.surfacePressure.toString().orDash())

        binding.content.visibility = View.VISIBLE
    }
}