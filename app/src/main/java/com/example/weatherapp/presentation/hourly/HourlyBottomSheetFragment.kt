package com.example.weatherapp.presentation.hourly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.databinding.FragmentBottomSheetWeatherBinding
import com.example.weatherapp.domain.model.Hourly
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime

private const val HOURS: Int = 24
class HourlyBottomSheetFragment(
    private val hourlyList: List<Hourly>,
    private val onItemClick: (Int) -> Unit
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "com.example.weatherapp.presentation.hourly.HourlyBottomSheetFragment"
    }

    private val currentDate = LocalDateTime.now()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentBottomSheetWeatherBinding.inflate(inflater, container, false).also {
            val filteredList = hourlyList.filter { it.time?.isBefore(currentDate) == false }.take(HOURS)
            it.list.adapter = HourlyAdapter(filteredList) { id ->
                dismiss()
                onItemClick(id)
            }
        }.root
}