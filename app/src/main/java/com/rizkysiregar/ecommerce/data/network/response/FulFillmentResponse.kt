package com.rizkysiregar.ecommerce.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FulFillmentResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: DataFulFillment,

	@field:SerializedName("message")
	val message: String
): Parcelable

@Parcelize
data class DataFulFillment(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("invoiceId")
	val invoiceId: String,

	@field:SerializedName("payment")
	val payment: String,

	@field:SerializedName("time")
	val time: String,

	@field:SerializedName("status")
	val status: Boolean
):Parcelable
