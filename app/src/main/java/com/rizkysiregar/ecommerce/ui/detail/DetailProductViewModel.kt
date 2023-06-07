package com.rizkysiregar.ecommerce.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity
import com.rizkysiregar.ecommerce.data.network.response.DetailProductResponse
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class DetailProductViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    private val _data = MutableLiveData<DetailProductResponse>()
    val data: MutableLiveData<DetailProductResponse> = _data

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getDetailProduct(productId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            contentRepository.getDetailProduct(productId) {isSuccess, responseBody, throwable ->
                if (isSuccess){
                    _isLoading.value = false
                    responseBody.let {
                        _data.value = it
                    }
                }else{
                    _isLoading.value = false
                    // error handle
                }
            }
        }
    }

    fun insertProductToCart(cartEntity: CartEntity) =
        contentRepository.insertNewProductToCart(cartEntity)


    fun insertNewWishlist(data: DetailEntity) =
        contentRepository.insertNewWishlist(data)


    // get data from DB
    private val _getAllDataFromDB = MutableLiveData<DetailEntity>()
    private val _getData = _getAllDataFromDB.switchMap {
        contentRepository.getWishlist()
    }

    val getAllWishList: LiveData<List<DetailEntity>> = _getData
}