package com.example.weatherapp.presentation.common

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentActivity
import com.example.weatherapp.R

class Background(
    private val activity: FragmentActivity,
    private val context: Context
) {

    fun set(@DrawableRes backgroundRes: Int) {
        val transition = TransitionDrawable(arrayOf(
            AppCompatResources.getDrawable(activity, R.color.theme_color),
            AppCompatResources.getDrawable(activity, backgroundRes)
        ))

        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.window.statusBarColor = context.getColor(android.R.color.transparent)
        activity.window.navigationBarColor = context.getColor(android.R.color.transparent)
        activity.window.setBackgroundDrawable(transition)
        transition.startTransition(1000)
    }
}