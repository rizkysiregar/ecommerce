package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.annotations.SerializedName

data class RatingResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)
