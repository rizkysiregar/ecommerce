package com.rizkysiregar.ecommerce.ui.cart

import androidx.lifecycle.ViewModel
import com.rizkysiregar.ecommerce.data.repository.ContentRepository

class CartViewModel(private val contentRepository: ContentRepository) : ViewModel() {
    // get all cart
    val getAllCart = contentRepository.getAllDataCart()
}