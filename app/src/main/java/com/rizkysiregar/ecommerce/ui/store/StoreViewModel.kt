package com.rizkysiregar.ecommerce.ui.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.network.response.ProductResponse
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class StoreViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    private val _data = MutableLiveData<ProductResponse>()
    val data: MutableLiveData<ProductResponse> = _data

    init {
        getProduct()
    }

    private fun getProduct() {
        viewModelScope.launch {
            contentRepository.getDataProduct { isSuccess, responseBody, trowable->

                if (isSuccess){
                    responseBody?.let {
                        _data.value = it
                    }
                }
            }
        }
    }
}