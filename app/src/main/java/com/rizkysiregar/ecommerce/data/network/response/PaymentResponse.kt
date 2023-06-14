package com.rizkysiregar.ecommerce.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.Field

data class PaymentResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class ItemsPayment(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("status")
	val status: Boolean
): Parcelable

data class DataItem(

	@field:SerializedName("item")
	val item: List<ItemsPayment>,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("status")
	val status: Boolean,
)
