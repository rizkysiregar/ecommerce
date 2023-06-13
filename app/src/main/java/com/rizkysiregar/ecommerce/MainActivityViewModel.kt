package com.rizkysiregar.ecommerce

import androidx.lifecycle.ViewModel
import com.rizkysiregar.ecommerce.data.repository.ContentRepository

class MainActivityViewModel(private val contentRepository: ContentRepository) : ViewModel() {
    val getItemCountWishList = contentRepository.getCountWishlist()
    val getItemCountCart = contentRepository.getItemCountCart()
}