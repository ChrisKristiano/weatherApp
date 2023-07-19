package com.example.weatherapp.presentation.daily.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentDailyDetailBinding
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.presentation.common.BackgroundController
import com.example.weatherapp.presentation.common.WeatherCodeTranslator
import com.example.weatherapp.presentation.main.MainViewModel
import com.example.weatherapp.presentation.util.extensions.orDash

class DailyDetailFragment : Fragment(R.layout.fragment_daily_detail) {

    private lateinit var binding: FragmentDailyDetailBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: DailyDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentDailyDetailBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getDailyById(args.dailyId)?.let { setupUI(it) }
    }

    private fun setupUI(data: Daily) {
        val newBgDrawable = WeatherCodeTranslator.toBackgroundDrawableRes(data.weatherCode, true)
        BackgroundController.set(newBgDrawable, requireActivity(), requireContext())

        binding.temperatureMax.text = getString(R.string.temperature, data.temperatureMax.toString().orDash())
        binding.temperatureMin.text = getString(R.string.temperature, data.temperatureMin.toString().orDash())
        binding.statusIcon.setImageResource(WeatherCodeTranslator.toIconDrawableRes(data.weatherCode, true))
        binding.feelsLikeMax.text = getString(R.string.temperature, data.apparentTemperatureMax.toString().orDash())
        binding.feelsLikeMin.text = getString(R.string.temperature, data.apparentTemperatureMin.toString().orDash())
        binding.sunrise.text = data.sunrise?.toLocalTime().toString().orDash()
        binding.sunset.text = data.sunset?.toLocalTime().toString().orDash()
        binding.rainChance.text = getString(R.string.percentage, data.precipitationProbability.toString().orDash())
        binding.windSpeed.text = getString(R.string.wind_speed_no_text, data.windSpeed.toString().orDash())
        binding.uvIndex.text = data.uvIndexMax.toString().orDash()

        binding.content.visibility = View.VISIBLE
    }
}