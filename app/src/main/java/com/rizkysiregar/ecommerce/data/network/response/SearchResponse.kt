package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<String>,

	@field:SerializedName("message")
	val message: String
)
