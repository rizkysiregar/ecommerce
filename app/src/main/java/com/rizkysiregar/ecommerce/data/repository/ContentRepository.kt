package com.rizkysiregar.ecommerce.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.data.network.api.ApiService
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import com.rizkysiregar.ecommerce.data.network.response.SearchResponse
import com.rizkysiregar.ecommerce.data.paging.ProductPagingSource
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentRepository(private val apiService: ApiService) {

    fun getDataProduct(query: QueryProductModel): LiveData<PagingData<ItemsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {
                ProductPagingSource(apiService, query)
            }
        ).liveData
    }

    suspend fun searchProduct(
        query: String,
        onResponse: (Boolean, SearchResponse?, throwable: String?) -> Unit
    ) {
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


//fun getDataProduct(
//    search: String?,
//    brand: String?,
//    lowest: Int?,
//    highest: Int?,
//    sort: String?,
//    onResponse: (Boolean, ProductResponse?, throwable: String?) -> Unit
//) {
//    apiService.getProduct(search,brand,lowest,highest,sort)
//        .enqueue(object : Callback<ProductResponse> {
//            override fun onResponse(
//                call: Call<ProductResponse>,
//                response: Response<ProductResponse>
//            ) {
//                val responseBody = response.body()
//                onResponse(response.isSuccessful, responseBody, null)
//            }
//
//            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
//                onResponse(false, null, t.message.toString())
//            }
//
//        })
//}