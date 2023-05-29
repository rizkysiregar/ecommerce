package com.rizkysiregar.ecommerce.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.network.response.SearchResponse
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    private val _data = MutableLiveData<SearchResponse>()
    val data: MutableLiveData<SearchResponse> = _data

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun searchProduct(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            contentRepository.searchProduct(query) { isSuccess, responseBody, throwable ->
                if (isSuccess){
                    _isLoading.value = false
                    responseBody?.let {
                        _data.value = it
                    }
                }else{
                    _isLoading.value = false
                    // throwable
                }
            }
        }
    }

}