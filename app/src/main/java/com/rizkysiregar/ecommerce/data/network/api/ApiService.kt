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

    //    @Headers("API_KEY: 6f8856ed-9189-488f-9011-0ff4b6c08edc")
    @POST("register")
    fun postRegister(
        @Body request: RequestBody
    ): Call<RegisterResponse>

    // Login
//    @Headers("API_KEY: 6f8856ed-9189-488f-9011-0ff4b6c08edc")
    @POST("login")
    fun postLoginUser(
        @Body request: RequestBody
    ): Call<RegisterResponse>


    @Multipart
    @POST("profile")
    fun postProfile(
//        @Header("Authorization") accessToken: String,
        @Part userImage: MultipartBody.Part,
        @Part("userName") userName: RequestBody,
    ): Call<ProfileResponse>
}