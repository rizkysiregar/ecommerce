package com.rizkysiregar.ecommerce.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import com.rizkysiregar.ecommerce.data.repository.ContentRepository

class StoreViewModel(private val contentRepository: ContentRepository) : ViewModel() {
    fun product(queryProductModel: QueryProductModel): LiveData<PagingData<ItemsItem>> =
        contentRepository.getDataProduct(queryProductModel).cachedIn(viewModelScope)

}