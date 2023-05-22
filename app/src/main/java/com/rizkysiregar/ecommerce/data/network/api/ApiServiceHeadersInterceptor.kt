package com.rizkysiregar.ecommerce.data.network.api

import android.content.Context
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class ApiServiceHeadersInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = PreferenceManager.getAccessToken(context)
        val refreshToken = PreferenceManager.getRefreshToken(context)
        val modifiedRequest = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $accessToken")
            .build()
        return  chain.proceed(modifiedRequest)
    }
}