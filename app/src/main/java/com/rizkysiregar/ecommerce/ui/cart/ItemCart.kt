package com.rizkysiregar.ecommerce.ui.cart

data class ItemCart(
    val id: Int,
    val price: Int,
    var isChecked: Boolean = false
)
