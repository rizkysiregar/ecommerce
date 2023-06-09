package com.rizkysiregar.ecommerce.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.network.response.PaymentResponse
import com.rizkysiregar.ecommerce.data.network.response.SearchResponse
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class PaymentViewModel(private val contentRepository: ContentRepository): ViewModel() {

    private val _data = MutableLiveData<PaymentResponse>()
    val data: MutableLiveData<PaymentResponse> = _data

    init {
        getPaymentMethod()
    }

    private fun getPaymentMethod() {
        viewModelScope.launch {
            contentRepository.getPaymentMethod(){isSUccess, responseBody, throwable ->
                if (isSUccess){
                    responseBody.let {
                        _data.value = it
                    }
                }else{
                    // handle error
                }
            }
        }
    }
}