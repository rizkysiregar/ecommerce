package com.rizkysiregar.ecommerce.data.network.api

import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.POST

interface ApiService {

    @POST("/register")
    fun postRegister(): Call<RegisterResponse>

}