package com.rizkysiregar.ecommerce.data.model

data class RegisterRequestModel(
    val accessToken: String,
    val expiresAt: Int,
    val refreshToken: String
)
