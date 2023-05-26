package com.rizkysiregar.ecommerce.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.network.response.SearchResponse
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    private val _data = MutableLiveData<SearchResponse>()
    val data: MutableLiveData<SearchResponse> = _data

    fun searchProduct(query: String) {
        viewModelScope.launch {
            contentRepository.searchProduct(query) { isSuccess, responseBody, throwable ->
                if (isSuccess){
                    responseBody?.let {
                        _data.value = it
                    }
                }else{
                    // throwable
                }
            }
        }
    }

}