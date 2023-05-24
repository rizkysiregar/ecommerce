package com.rizkysiregar.ecommerce.data.model

import com.google.gson.annotations.SerializedName

data class RefreshModel(
	@field:SerializedName("token")
	val token: String
)
