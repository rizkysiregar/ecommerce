package com.rizkysiregar.ecommerce.data.repository

import com.rizkysiregar.ecommerce.data.model.RegisterRequestModel
import com.rizkysiregar.ecommerce.data.network.RemoteDataSource
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse

import okhttp3.RequestBody
import retrofit2.Callback

class UserRepository(private val remoteDataSource: RemoteDataSource) {
    fun registerUser(
        email: String,
        password: String,
        callback: Callback<RegisterResponse>
    ){
        remoteDataSource.registerUser(email,password, callback)
    }
}