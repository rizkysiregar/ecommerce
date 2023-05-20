package com.rizkysiregar.ecommerce.data.repository

import com.rizkysiregar.ecommerce.data.model.RegisterModel
import com.rizkysiregar.ecommerce.data.network.api.ApiService
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository(private val apiService: ApiService) {

    fun registerUser(request: RequestBody, onResponse: (Boolean, RegisterResponse?) -> Unit) {
        apiService.postRegister(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                onResponse(response.isSuccessful, responseBody)
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onResponse(false, null)
            }
        })
    }

    fun loginUser(request: RequestBody, onResponse: (Boolean, RegisterResponse?) -> Unit) {
        apiService.postLoginUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                onResponse(response.isSuccessful, responseBody)
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onResponse(false, null)
            }
        })
    }
}