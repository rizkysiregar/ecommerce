package com.rizkysiregar.ecommerce.data.model


import com.google.gson.annotations.SerializedName




data class FulFillmentRequestModel(
    @SerializedName("payment") val payment: String,
    @SerializedName("items") val items: List<ItemModel>
)


data class ItemModel(
    @SerializedName("productId") val productId: String,
    @SerializedName("variantName") val variantName: String,
    @SerializedName("quantity") val quantity: Int
)