package com.rizkysiregar.ecommerce.ui.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rizkysiregar.ecommerce.data.model.FulFillmentRequestModel
import com.rizkysiregar.ecommerce.data.network.response.FulFillmentResponse
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class CheckoutViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    private val _data = MutableLiveData<FulFillmentResponse>()
    val data: MutableLiveData<FulFillmentResponse> = _data
    fun buyProduct(data: FulFillmentRequestModel) {
        viewModelScope.launch {
            contentRepository.fulFillment(data) { isSuccess, responseBody, throwable ->
                if (isSuccess){
                    responseBody?.let {
                        _data.value = it
                    }
                }else{
                    //
                }
            }
        }
    }
}