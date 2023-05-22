package com.rizkysiregar.ecommerce.ui.register


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rizkysiregar.ecommerce.data.model.RegisterModel
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import com.rizkysiregar.ecommerce.data.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.RequestBody.Companion.toRequestBody

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _data = MutableLiveData<RegisterResponse>()
    val data: MutableLiveData<RegisterResponse> = _data


    fun registerNewUser(data: RegisterModel) {
        viewModelScope.launch {
            val requestBodyString = Gson().toJson(data)
            val mediaType = "application/json".toMediaTypeOrNull()
            val requestBody = requestBodyString.toRequestBody(mediaType)

            userRepository.registerUser(requestBody) { isSuccess, responseBody ->
                if (isSuccess) {
                    responseBody?.let {
                        _data.value = it
                    }
                } else {
                    //
                }
            }
        }
    }
}