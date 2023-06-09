package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.annotations.SerializedName

data class PaymentResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class ItemsPayment(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class DataItem(

	@field:SerializedName("item")
	val item: List<ItemsPayment>,

	@field:SerializedName("title")
	val title: String
)
