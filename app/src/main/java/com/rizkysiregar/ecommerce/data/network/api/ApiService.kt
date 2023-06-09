package com.rizkysiregar.ecommerce.data.network.api

import com.rizkysiregar.ecommerce.data.network.response.DetailProductResponse
import com.rizkysiregar.ecommerce.data.network.response.PaymentResponse
import com.rizkysiregar.ecommerce.data.network.response.ProductResponse
import com.rizkysiregar.ecommerce.data.network.response.ProfileResponse
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import com.rizkysiregar.ecommerce.data.network.response.ResponseReview
import com.rizkysiregar.ecommerce.data.network.response.SearchResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    // Register New User
    @POST("register")
    fun postRegister(
        @Body request: RequestBody
    ): Call<RegisterResponse>

    // Login
    @POST("login")
    fun postLoginUser(
        @Body request: RequestBody
    ): Call<RegisterResponse>

    // refresh token
    @POST("refresh")
    fun postRefreshToken(
        @Body request: RequestBody
    ): Call<RegisterResponse>

    @Multipart
    @POST("profile")
    fun postProfile(
        @Part userImage: MultipartBody.Part,
        @Part("userName") userName: RequestBody,
    ): Call<ProfileResponse>

    // all products
    @POST("products")
    suspend fun getProducts(
        @Query("search") search: String?,
        @Query("brand") brand: String?,
        @Query("lowest") lowest: Int?,
        @Query("highest") highest: Int?,
        @Query("sort") sort: String?,
        @Query("limit") limit: Int?,
        @Query("page") page: Int?
    ): ProductResponse


    @POST("search")
    fun searchProduct(
        @Query("query") query: String
    ): Call<SearchResponse>

    @GET("products")
    fun getDetailProduct(
        @Query("id") id: String
    ): Call<DetailProductResponse>


    // get review
    @GET("review")
    fun getReviewById(
        @Query("id") id: String
    ): Call<ResponseReview>

    // payment method
    @GET("payment")
    fun getPayment(): Call<PaymentResponse>

}