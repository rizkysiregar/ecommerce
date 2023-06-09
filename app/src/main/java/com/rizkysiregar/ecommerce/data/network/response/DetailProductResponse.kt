package com.rizkysiregar.ecommerce.data.network.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity(tableName = "tbl_wishlist")
data class DetailEntity(

    @ColumnInfo("image")
    val image: String,

    @PrimaryKey
    @ColumnInfo("productId")
    val productId: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("totalRating")
    val totalRating: Int,

    @ColumnInfo("store")
    val store: String,

    @ColumnInfo("productName")
    val productName: String,

    @ColumnInfo("totalSatisfaction")
    val totalSatisfaction: Int,

    @ColumnInfo("sale")
    val sale: Int,

    @ColumnInfo("stock")
    val stock: Int,

    @ColumnInfo("productRating")
    val productRating: String,

    @ColumnInfo("brand")
    val brand: String,

    @ColumnInfo("productPrice")
    val productPrice: Int,

    @ColumnInfo("totalReview")
    val totalReview: Int,
    )

@Entity(tableName = "tbl_cart")
data class CartEntity(

    @ColumnInfo("image")
    val image: String,

    @PrimaryKey
    @ColumnInfo("productId")
    val productId: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("totalRating")
    val totalRating: Int,

    @ColumnInfo("store")
    val store: String,

    @ColumnInfo("productName")
    val productName: String,

    @ColumnInfo("totalSatisfaction")
    val totalSatisfaction: Int,

    @ColumnInfo("sale")
    val sale: Int,

    @ColumnInfo("stock")
    val stock: Int,

    @ColumnInfo("productRating")
    val productRating: String,

    @ColumnInfo("brand")
    val brand: String,

    @ColumnInfo("productPrice")
    val productPrice: Int,

    @ColumnInfo("totalReview")
    val totalReview: Int,

    @ColumnInfo("variantName")
    val variantName: String,

    @ColumnInfo("isChecked")
    var isChecked: Boolean = false
)
