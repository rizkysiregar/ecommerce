package com.rizkysiregar.ecommerce.data.network.api

import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    // Register New User
    @POST("register")
    fun postRegister(
        @Body request: RequestBody
    ): Call<RegisterResponse>

    // Login
    @POST("login")
    fun postLoginUser(
        @Body request: RequestBody
    ): Call<RegisterResponse>
}