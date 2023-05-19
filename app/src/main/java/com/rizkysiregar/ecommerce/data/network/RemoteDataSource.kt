package com.rizkysiregar.ecommerce.data.network

import android.util.Log
import com.rizkysiregar.ecommerce.data.model.RegisterRequestModel
import com.rizkysiregar.ecommerce.data.network.api.ApiService
import com.rizkysiregar.ecommerce.data.network.api.ApiResponse
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import retrofit2.Callback
import java.lang.Exception

class RemoteDataSource constructor(private val apiService: ApiService) {

    fun registerUser(
        email: String,
        password: String,
        callback: Callback<RegisterResponse>
    ) {

    }
}