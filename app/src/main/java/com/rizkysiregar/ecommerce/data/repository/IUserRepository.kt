package com.rizkysiregar.ecommerce.data.repository

import com.rizkysiregar.ecommerce.data.model.RegisterRequestModel
import com.rizkysiregar.ecommerce.data.network.Resource
import com.rizkysiregar.ecommerce.data.network.api.ApiResponse
import com.rizkysiregar.ecommerce.data.network.response.Data
import kotlinx.coroutines.flow.Flow


interface IUserRepository {
    suspend fun postRegisterUser(email: String, password: String): Flow<Resource<RegisterRequestModel>>
}