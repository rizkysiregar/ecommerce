package com.rizkysiregar.ecommerce.data.network.api

import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    companion object {
        private const val API_KEY = "6f8856ed-9189-488f-9011-0ff4b6c08edc"
    }

    // Register New User
    @FormUrlEncoded
    @Headers(
        "Content-Type: application/json",
        "API_KEY: $API_KEY"
    )
    @POST("register")
    suspend fun postRegister(
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

}