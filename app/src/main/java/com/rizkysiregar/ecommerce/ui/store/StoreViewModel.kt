package com.rizkysiregar.ecommerce.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import com.rizkysiregar.ecommerce.data.network.response.ProductResponse
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class StoreViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    private val query = QueryProductModel(null,null,null,null,null)
    val product: LiveData<PagingData<ItemsItem>> =
        contentRepository.getDataProduct(query).cachedIn(viewModelScope)


    private val _data = MutableLiveData<ProductResponse>()
    val data: MutableLiveData<ProductResponse> = _data


//    fun getProduct(
//        search: String?,
//        brand: String?,
//        lowest: Int?,
//        highest: Int?,
//        sort: String?,
//    ) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            contentRepository.getDataProduct(
//                search,
//                brand,
//                lowest,
//                highest,
//                sort
//            ) { isSuccess, responseBody, trowable ->
//                if (isSuccess) {
//                    _isLoading.value = false
//                    responseBody?.let {
//                        _data.value = it
//                    }
//                } else {
//                    _isLoading.value = false
//                }
//            }
//        }
//    }

}