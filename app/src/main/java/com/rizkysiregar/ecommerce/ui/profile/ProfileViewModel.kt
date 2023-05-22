package com.rizkysiregar.ecommerce.ui.profile

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.model.ProfileModel
import com.rizkysiregar.ecommerce.data.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.URI

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun postProfile(data: ProfileModel) {
        viewModelScope.launch {
            val userName = data.userName
            val userNameRequestBody = userName.toRequestBody(("text/plain".toMediaTypeOrNull()))

            val imageFile = File(URI.create(data.userName))
            val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)

            userRepository.profileUser(imagePart, userNameRequestBody){isSuccess, responseBody ->
                if (isSuccess){
                    responseBody?.let {
                    }
                }
            }
        }
    }

    // Function to convert drawable to bitmap
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

}