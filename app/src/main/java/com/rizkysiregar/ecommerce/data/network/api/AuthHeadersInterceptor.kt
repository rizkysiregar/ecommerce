package com.rizkysiregar.ecommerce.data.network.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthHeadersInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedRequest = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .header("API_KEY", "6f8856ed-9189-488f-9011-0ff4b6c08edc")
            .build()
        return  chain.proceed(modifiedRequest)
    }
}