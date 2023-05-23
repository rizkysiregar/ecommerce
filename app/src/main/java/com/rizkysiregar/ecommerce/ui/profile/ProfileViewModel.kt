package com.rizkysiregar.ecommerce.ui.profile

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.model.ProfileModel
import com.rizkysiregar.ecommerce.data.network.response.ProfileResponse
import com.rizkysiregar.ecommerce.data.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _data = MutableLiveData<ProfileResponse>()
    val data: MutableLiveData<ProfileResponse> = _data


    private var filePart: MultipartBody.Part? = null
    fun postProfile(data: ProfileModel) {
        viewModelScope.launch {
            val userName = data.userName.toRequestBody()
            data.userImage.let {
                val requestBody = it.asRequestBody("image/*".toMediaTypeOrNull())
                filePart = MultipartBody.Part.createFormData(
                    "userImage",
                    "profile.png",
                    requestBody
                )
            }

            filePart?.let {
                userRepository.profileUser(userName, it) { isSuccess, responseBody ->
                    if (isSuccess) {
                        responseBody?.let {
                            _data.value = it
                        }
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