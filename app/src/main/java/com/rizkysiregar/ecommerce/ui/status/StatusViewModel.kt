package com.rizkysiregar.ecommerce.ui.status

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.model.RatingModel
import com.rizkysiregar.ecommerce.data.network.response.RatingResponse
import com.rizkysiregar.ecommerce.data.network.response.SearchResponse
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class StatusViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    private val _data = MutableLiveData<RatingResponse>()
    val data: MutableLiveData<RatingResponse> = _data

    fun postStatusOrder(rating: RatingModel) {
        viewModelScope.launch {
            contentRepository.ratingProduct(rating) { isSuccessful, responseBody, throwable ->
                if (isSuccessful){
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