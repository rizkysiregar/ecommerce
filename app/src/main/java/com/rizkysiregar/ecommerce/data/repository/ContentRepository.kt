package com.rizkysiregar.ecommerce.data.repository

import com.rizkysiregar.ecommerce.data.network.api.ApiService
import com.rizkysiregar.ecommerce.data.network.response.ProductResponse
import com.rizkysiregar.ecommerce.data.network.response.SearchResponse
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentRepository(private val apiService: ApiService) {

    fun getDataProduct(onResponse: (Boolean, ProductResponse?, throwable: String?) -> Unit) {
        apiService.getProduct().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                val responseBody = response.body()
                onResponse(response.isSuccessful, responseBody, null)
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                onResponse(false, null, t.message.toString())
            }

        })
    }

    suspend fun searchProduct(query: String, onResponse: (Boolean, SearchResponse?, throwable: String?) -> Unit) {
        delay(2000)
        apiService.searchProduct(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val responseBody = response.body()
                onResponse(response.isSuccessful, responseBody, null)
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                onResponse(false, null, t.message.toString())
            }

        })
    }
}