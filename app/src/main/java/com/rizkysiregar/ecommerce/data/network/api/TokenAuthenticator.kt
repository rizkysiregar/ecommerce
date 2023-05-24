package com.rizkysiregar.ecommerce.data.network.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import com.rizkysiregar.ecommerce.data.model.RefreshModel
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TokenAuthenticator(private val context: Context) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.code == 401) {
            val chuckerCollector = ChuckerCollector(
                context = context,
                showNotification = true,
                retentionPeriod = RetentionManager.Period.ONE_HOUR
            )

            val chuckerInterceptor = ChuckerInterceptor.Builder(context)
                .collector(chuckerCollector)
                .maxContentLength(250_000L)
                .redactHeaders("Auth-Token", "Bearer")
                .alwaysReadResponseBody(true)
                .build()

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(ApiServiceHeadersInterceptor(context))
                .addInterceptor(chuckerInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://172.17.20.85:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            val refreshToken = PreferenceManager.getRefreshToken(context)
            val modelRefresh = RefreshModel(refreshToken.toString())
            val requestBodyString = Gson().toJson(modelRefresh)
            val mediaType = "application/json".toMediaTypeOrNull()
            val requestBody = requestBodyString.toRequestBody(mediaType)
            val apiService = retrofit.create(ApiService::class.java)

            synchronized(this) {
                try {
                    val refreshTokenResponse = apiService.postRefreshToken(requestBody).execute()

                    if (refreshTokenResponse.isSuccessful) {
                        val newAccessToken = refreshTokenResponse.body()?.data?.accessToken
                        val newRefreshToken = refreshTokenResponse.body()?.data?.refreshToken
                        if (!newAccessToken.isNullOrEmpty()) {
                            PreferenceManager.setAccessToken(context, newAccessToken.toString())
                            PreferenceManager.setRefreshToken(context, newRefreshToken.toString())
                        } else {
                            //
                        }
                    } else {
                        Toast.makeText(context, refreshTokenResponse.message(), Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    Log.e("Error Auth: ", e.message.toString())
                }
            }
        }
        return null
    }
}