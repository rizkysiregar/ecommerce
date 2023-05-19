package com.rizkysiregar.ecommerce.ui.register

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkysiregar.ecommerce.data.model.RegisterRequestModel
import com.rizkysiregar.ecommerce.data.network.response.Data
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import com.rizkysiregar.ecommerce.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    companion object {
        const val TAG = "Register"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    suspend fun userRegister(email: String, password: String){
        withContext(Dispatchers.IO){
            userRepository.postRegisterUser(email,password)
        }
    }


//    fun postRegister(email: String, password: String) {
//        userRepository.registerUser(email, password , object : Callback<RegisterResponse> {
//            override fun onResponse(
//                call: Call<RegisterResponse>,
//                response: Response<RegisterResponse>
//            ) {
//                _isLoading.value = false
//                val responseBody = response.body()
//                if (response.isSuccessful && responseBody != null) {
//                    sessionUser(data = responseBody.data)
//                }
//            }
//
//            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//
//        })
//    }

//    fun sessionUser(data: Data){
//        Log.d(TAG, data.accessToken)
//    }


}