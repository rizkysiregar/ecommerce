package com.rizkysiregar.ecommerce.data.local.preference

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREF_NAME = "OnboardPref"
    private const val KEY_ONBOARDING_COMPLATED = "OnboardingCompleted"

    private fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setOnboardoingPreference(context: Context, completed: Boolean) {
        val editor = getSharedPreference(context).edit()
        editor.putBoolean(KEY_ONBOARDING_COMPLATED, completed)
        editor.apply()
    }

    fun isOnboardingCompleted(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(KEY_ONBOARDING_COMPLATED, false)
    }
}