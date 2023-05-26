package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("pageIndex")
	val pageIndex: Int,

	@field:SerializedName("itemsPerPage")
	val itemsPerPage: Int,

	@field:SerializedName("currentItemCount")
	val currentItemCount: Int,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("sale")
	val sale: Int,

	@field:SerializedName("productId")
	val productId: String,

	@field:SerializedName("store")
	val store: String,

	@field:SerializedName("productRating")
	val productRating: Any,

	@field:SerializedName("brand")
	val brand: String,

	@field:SerializedName("productName")
	val productName: String,

	@field:SerializedName("productPrice")
	val productPrice: Int
)
