package com.rizkysiregar.ecommerce.data.network.api

import com.rizkysiregar.ecommerce.data.network.response.ProfileResponse
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
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

    @Multipart
    @POST("profile")
    fun postProfile(
        @Part userImage: MultipartBody.Part,
        @Part("userName") userName: RequestBody
    ): Call<ProfileResponse>
}