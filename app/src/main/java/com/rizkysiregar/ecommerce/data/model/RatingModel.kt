package com.rizkysiregar.ecommerce.data.model

import com.google.gson.annotations.SerializedName

data class RatingModel(

	@field:SerializedName("review")
	val review: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("invoiceId")
	val invoiceId: String
)
