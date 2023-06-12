package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItemTransaction>,

	@field:SerializedName("message")
	val message: String
)

data class ItemsItemTransaction(

	@field:SerializedName("quantity")
	val quantity: Int,

	@field:SerializedName("productId")
	val productId: String,

	@field:SerializedName("variantName")
	val variantName: String
)

data class DataItemTransaction(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("review")
	val review: String,

	@field:SerializedName("rating")
	val rating: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("invoiceId")
	val invoiceId: String,

	@field:SerializedName("payment")
	val payment: String,

	@field:SerializedName("time")
	val time: String,

	@field:SerializedName("items")
	val items: List<ItemsItemTransaction>,

	@field:SerializedName("status")
	val status: Boolean
)
