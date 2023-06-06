package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.annotations.SerializedName

data class ResponseReview(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItemReview>,

	@field:SerializedName("message")
	val message: String,
)

data class DataItemReview(

	@field:SerializedName("userImage")
	val userImage: String,

	@field:SerializedName("userName")
	val userName: String,

	@field:SerializedName("userReview")
	val userReview: String,

	@field:SerializedName("userRating")
	val userRating: Int
)
