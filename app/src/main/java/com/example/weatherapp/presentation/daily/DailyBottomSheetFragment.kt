package com.example.weatherapp.presentation.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.databinding.BottomSheetWeekBinding
import com.example.weatherapp.domain.model.Daily
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DailyBottomSheetFragment(
    private val dailyList: List<Daily>
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "com.example.weatherapp.presentation.daily.DailyBottomSheet"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        BottomSheetWeekBinding.inflate(inflater, container, false).also {
            it.dailyList.adapter = DailyAdapter(dailyList)
        }.root
}