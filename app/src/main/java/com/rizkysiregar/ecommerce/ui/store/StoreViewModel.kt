package com.rizkysiregar.ecommerce.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import com.rizkysiregar.ecommerce.data.repository.ContentRepository

class StoreViewModel(private val contentRepository: ContentRepository) : ViewModel() {
//    fun product(queryProductModel: QueryProductModel): LiveData<PagingData<ItemsItem>> =
//        contentRepository.getDataProduct(queryProductModel).cachedIn(viewModelScope)
    private var baseQueryFilter: QueryProductModel = QueryProductModel(null, null, null, null, null)


    private var _query = MutableLiveData<QueryProductModel>()
    private val _result = _query.switchMap { query ->
        contentRepository.getDataProduct(query).cachedIn(viewModelScope)
    }

    val product: LiveData<PagingData<ItemsItem>> = _result


    // for set productId
    fun setQuery(query: QueryProductModel){
        if (query == _query.value){
            return
        }
        _query.value = query
    }

}