package com.rizkysiregar.ecommerce.data.network.api

import android.content.Context
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class ApiServiceHeadersInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val endpoint = chain.request().url.encodedPath
        val accessToken = PreferenceManager.getAccessToken(context)
        val refreshToken = PreferenceManager.getRefreshToken(context)
        if (endpoint == "/register" || endpoint == "/login") {
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("API_KEY", "6f8856ed-9189-488f-9011-0ff4b6c08edc")
                .build()
            return chain.proceed(modifiedRequest)
        } else {
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()

                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer $accessToken")
                .build()
            return chain.proceed(modifiedRequest)
        }


    }
}