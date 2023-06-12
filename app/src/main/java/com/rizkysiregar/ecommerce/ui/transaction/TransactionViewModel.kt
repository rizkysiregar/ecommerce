package com.rizkysiregar.ecommerce.ui.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.network.response.TransactionResponse
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class TransactionViewModel(val contentRepository: ContentRepository) : ViewModel() {

    private val _data = MutableLiveData<TransactionResponse>()
    val data: MutableLiveData<TransactionResponse> = _data

    init {
        getTransaction()
    }

    private fun getTransaction() {
        viewModelScope.launch {
            contentRepository.getTransaction { isSuccessfull, responseBody, throwable ->
                if (isSuccessfull){
                    responseBody?.let {
                        _data.value = it
                    }
                }else{
                    // throw
                }
            }
        }
    }
}