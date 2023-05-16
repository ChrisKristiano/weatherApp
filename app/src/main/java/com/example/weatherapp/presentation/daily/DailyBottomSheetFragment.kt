package com.example.weatherapp.presentation.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.databinding.FragmentBottomSheetWeatherBinding
import com.example.weatherapp.domain.model.Daily
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DailyBottomSheetFragment(
    private val dailyList: List<Daily>,
    private val onItemClick: (Int) -> Unit
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "com.example.weatherapp.presentation.daily.DailyBottomSheet"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentBottomSheetWeatherBinding.inflate(inflater, container, false).also {
            it.list.adapter = DailyAdapter(dailyList) { id ->
                dismiss()
                onItemClick(id)
            }
        }.root
}