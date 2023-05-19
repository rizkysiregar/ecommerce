package com.rizkysiregar.ecommerce.data.repository


import com.rizkysiregar.ecommerce.data.model.RegisterRequestModel
import com.rizkysiregar.ecommerce.data.network.NetworkResourceBound
import com.rizkysiregar.ecommerce.data.network.RemoteDataSource
import com.rizkysiregar.ecommerce.data.network.Resource
import com.rizkysiregar.ecommerce.data.network.api.ApiResponse
import com.rizkysiregar.ecommerce.data.network.response.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(private val remoteDataSource: RemoteDataSource) : IUserRepository {
    override suspend fun postRegisterUser(email: String, password: String): Flow<Resource<RegisterRequestModel>> =
//        remoteDataSource.registerUser(email, password)
        object : NetworkResourceBound<RegisterRequestModel, Data>(){
            override suspend fun loadFromDB(): Flow<RegisterRequestModel> {
                return flow {
                    RegisterRequestModel("asadada",12,"adadasdasdasda")
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<Data>> =
                remoteDataSource.registerUser(email, password)


            override fun shouldFetch(data: RegisterRequestModel?): Boolean =
                true

        }.asFlow()

}