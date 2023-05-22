package com.rizkysiregar.ecommerce.data.local.preference

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {

    private const val ONBOARDING_PREF = "OnboardPref"
    private const val KEY_ONBOARDING_COMPLATED = "OnboardingCompleted"

    private const val ACCESS_T0KEN_PREF = "AccessTokenPref"
    private const val KEY_ACCESS_TOKEN = "AccessToken"

    private const val REFRESH_TOKEN_PREF = "RefreshTokenPref"
    private const val KEY_REFRESH_TOKEN = "RefreshToken"

    // On-boarding
    private fun getSharedPreference(context: Context, pref: String): SharedPreferences {
        return context.getSharedPreferences(pref, Context.MODE_PRIVATE)
    }

    fun setOnboardoingPreference(context: Context, completed: Boolean) {
        val editor = getSharedPreference(context, ONBOARDING_PREF).edit()
        editor.putBoolean(KEY_ONBOARDING_COMPLATED, completed)
        editor.apply()
    }

    fun isOnboardingCompleted(context: Context): Boolean {
        return getSharedPreference(context, ONBOARDING_PREF).getBoolean(
            KEY_ONBOARDING_COMPLATED,
            false
        )
    }

    // access token
    fun setAccessToken(context: Context, token: String) {
        val editor = getSharedPreference(context, ACCESS_T0KEN_PREF).edit()
        editor.putString(KEY_ACCESS_TOKEN, token)
        editor.apply()
    }

    fun getAccessToken(context: Context): String? {
        return getSharedPreference(context, ACCESS_T0KEN_PREF).getString(KEY_ACCESS_TOKEN, "")
    }


    // refresh token
    fun setRefreshToken(context: Context, token: String) {
        val editor = getSharedPreference(context, REFRESH_TOKEN_PREF).edit()
        editor.putString(KEY_REFRESH_TOKEN, token)
        editor.apply()
    }

    fun getRefreshToken(context: Context): String? {
        return getSharedPreference(context, REFRESH_TOKEN_PREF).getString(KEY_REFRESH_TOKEN, "")
    }


}