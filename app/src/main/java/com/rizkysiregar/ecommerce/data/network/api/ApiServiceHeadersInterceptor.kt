package com.rizkysiregar.ecommerce.data.network.api

import android.content.Context
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class ApiServiceHeadersInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // get token
        val accessToken = PreferenceManager.getAccessToken(context).toString()

        // get url
        val url = chain.request().url.encodedPath

        val isAuthUrl = url.contains("register") ||
                url.contains("login") ||
                url.contains("refresh")

        val headerName = if (isAuthUrl) "API_KEY" else "Authorization"
        val headerValue = if (isAuthUrl) "6f8856ed-9189-488f-9011-0ff4b6c08edc" else "Bearer $accessToken"

        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(headerName, headerValue)
            .build()

        return chain.proceed(newRequest)
    }
}
