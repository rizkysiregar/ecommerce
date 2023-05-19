package com.rizkysiregar.ecommerce.data.network

import android.util.Log
import com.rizkysiregar.ecommerce.data.network.api.ApiService
import com.rizkysiregar.ecommerce.data.network.api.ApiResponse
import com.rizkysiregar.ecommerce.data.network.response.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource constructor(private val apiService: ApiService) {

    suspend fun registerUser(email: String, password: String): Flow<ApiResponse<Data>> {
        return flow {
            try {
                val response = apiService.postRegister(email, password)
                val data = response.data

                if (data.accessToken.isNotEmpty()) {
                   emit(ApiResponse.Success(response.data))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}