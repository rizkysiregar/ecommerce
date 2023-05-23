package com.rizkysiregar.ecommerce.data.model

import android.graphics.drawable.Drawable
import okhttp3.MultipartBody
import java.io.File

data class ProfileModel(
    val userName: String,
    val userImage: File
)
