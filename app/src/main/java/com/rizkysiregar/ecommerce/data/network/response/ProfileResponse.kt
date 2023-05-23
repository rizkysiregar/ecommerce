package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: DataProfile,

    @field:SerializedName("message")
    val message: String
)

data class DataProfile(

    @field:SerializedName("userImage")
    val userImage: String,

    @field:SerializedName("userName")
    val userName: String
)
