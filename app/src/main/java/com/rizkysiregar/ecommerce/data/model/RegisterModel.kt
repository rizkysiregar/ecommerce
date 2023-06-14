package com.rizkysiregar.ecommerce.data.model

import com.google.gson.annotations.SerializedName

data class RegisterLoginModel(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("firebaseToken")
    val firebaseToken: String
)
