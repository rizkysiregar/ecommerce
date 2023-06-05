package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: DataDetail,

    @field:SerializedName("message")
    val message: String
)

data class ProductVariantItem(

    @field:SerializedName("variantPrice")
    val variantPrice: Int,

    @field:SerializedName("variantName")
    val variantName: String
)

data class DataDetail(

    @field:SerializedName("image")
    val image: List<String>,

    @field:SerializedName("productId")
    val productId: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("totalRating")
    val totalRating: Int,

    @field:SerializedName("store")
    val store: String,

    @field:SerializedName("productName")
    val productName: String,

    @field:SerializedName("totalSatisfaction")
    val totalSatisfaction: Int,

    @field:SerializedName("sale")
    val sale: Int,

    @field:SerializedName("productVariant")
    val productVariant: List<ProductVariantItem>,

    @field:SerializedName("stock")
    val stock: Int,

    @field:SerializedName("productRating")
    val productRating: Any,

    @field:SerializedName("brand")
    val brand: String,

    @field:SerializedName("productPrice")
    val productPrice: Int,

    @field:SerializedName("totalReview")
    val totalReview: Int
)
