package com.rizkysiregar.ecommerce.data.network.api

import android.content.Context
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ApiServiceHeadersInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val endpoint = chain.request().url.encodedPath
        val accessToken = PreferenceManager.getAccessToken(context)
        val refreshToken = PreferenceManager.getRefreshToken(context)
        val originalRequest = chain.request()

        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${refreshToken.toString()}")
            .build()

        val response = chain.proceed(authenticatedRequest)

        if (response.code == 401) {
            val refreshedAccessToken = refreshToken.toString()
            if (refreshedAccessToken != null) {
                val retryRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer ${refreshToken.toString()}")
                    .build()
                return chain.proceed(retryRequest)
            }
        }

        return if (endpoint == "/register" || endpoint == "/login" || endpoint == "/refresh") {
            val modifiedRequest = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("API_KEY", "6f8856ed-9189-488f-9011-0ff4b6c08edc")
                .build()
            chain.proceed(modifiedRequest)
        } else {
            val modifiedRequest = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer ${accessToken.toString()}")
                .build()
            chain.proceed(modifiedRequest)

        }
    }
}