package com.rizkysiregar.ecommerce.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class CartViewModel(private val contentRepository: ContentRepository) : ViewModel() {
    // get all cart
    val getAllCart = contentRepository.getAllDataCart()

    // get count
    val getCountOfFalseValue = contentRepository.getCountOfFalseValues()


    // get product that selected
    val getSelectedProduct = contentRepository.getProductCartThatSelected()

    // update checkbox selected
    fun setSelectedProduct(cartEntity: CartEntity, state: Boolean) {
        viewModelScope.launch {
            contentRepository.setProductCartSelected(cartEntity, state)
        }
    }

    // update quantity of product
    fun setQuantityProduct(cartEntity: CartEntity){
        viewModelScope.launch {
            contentRepository.setProductQuantity(cartEntity)
        }
    }

    fun delete(cartEntity: CartEntity) {
        contentRepository.deleteCartEntity(cartEntity)
    }
}