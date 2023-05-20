package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,
)

data class Data(

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("expiresAt")
	val expiresAt: Int,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)
