package com.rizkysiregar.ecommerce.data.model

data class QueryProductModel(
    var search: String?,
    var brand: String?,
    var lowest: Int?,
    var highest: Int?,
    var sort: String?,
)
