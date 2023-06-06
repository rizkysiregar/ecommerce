package com.rizkysiregar.ecommerce.ui.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.network.response.ResponseReview
import com.rizkysiregar.ecommerce.data.network.response.SearchResponse
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class ReviewViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    private val _data = MutableLiveData<ResponseReview>()
    val data: MutableLiveData<ResponseReview> = _data

    fun getReviewById(productId: String){
        viewModelScope.launch {
            contentRepository.getReviewById(productId){isSuccess, responseBody, throwable ->
                if (isSuccess){
                    responseBody.let {
                        _data.value = it
                    }
                }else{
                    // do something
                }
            }
        }
    }

}