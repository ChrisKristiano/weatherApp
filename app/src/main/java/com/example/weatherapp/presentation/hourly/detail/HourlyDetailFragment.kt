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

        binding.temperature.text = data.temperature.toString()
        binding.statusIcon.setImageResource(WeatherCodeTranslator.toIconDrawableRes(data.weatherCode, data.isDay))
        binding.content.visibility = View.VISIBLE
    }
}