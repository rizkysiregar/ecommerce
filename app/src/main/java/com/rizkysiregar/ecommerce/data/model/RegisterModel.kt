package com.rizkysiregar.ecommerce.data.model

import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,
)
