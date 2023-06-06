package com.rizkysiregar.ecommerce.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rizkysiregar.ecommerce.data.local.db.AppExecutors
import com.rizkysiregar.ecommerce.data.local.db.EcommerceDao
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.data.network.api.ApiService
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity
import com.rizkysiregar.ecommerce.data.network.response.DetailProductResponse
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import com.rizkysiregar.ecommerce.data.network.response.ResponseReview
import com.rizkysiregar.ecommerce.data.network.response.SearchResponse
import com.rizkysiregar.ecommerce.data.paging.ProductPagingSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentRepository(
    private val apiService: ApiService,
    private val ecommerceDao: EcommerceDao,
    private val appExecutors: AppExecutors
) {
    @OptIn(DelicateCoroutinesApi::class)
    fun deleteWishlist(data: DetailEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            ecommerceDao.deleteWishlist(data)
        }
    }

    fun getWishlist(): LiveData<List<DetailEntity>> {
        return ecommerceDao.getAllDataFromWishlist()
    }

    fun insertNewWishlist(data: DetailEntity) {
        appExecutors.diskIO().execute { ecommerceDao.insertNewWishlist(data) }
    }

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

    fun getDetailProduct(
        productId: String,
        onResponse: (Boolean, DetailProductResponse?, throwable: String?) -> Unit
    ) {
        apiService.getDetailProduct(productId).enqueue(object : Callback<DetailProductResponse> {
            override fun onResponse(
                call: Call<DetailProductResponse>,
                response: Response<DetailProductResponse>
            ) {
                val responseBody = response.body()
                onResponse(response.isSuccessful, responseBody, null)
            }

            override fun onFailure(call: Call<DetailProductResponse>, t: Throwable) {
                onResponse(false, null, t.message.toString())
            }
        })
    }

    fun getReviewById(
        productId: String,
        onResponse: (Boolean, ResponseReview?, throwable: String?) -> Unit
    ) {
        apiService.getReviewById(productId).enqueue(object : Callback<ResponseReview> {
            override fun onResponse(
                call: Call<ResponseReview>,
                response: Response<ResponseReview>
            ) {
                val responseBody = response.body()
                onResponse(response.isSuccessful, responseBody, null)
            }

            override fun onFailure(call: Call<ResponseReview>, t: Throwable) {
                onResponse(false, null, t.message.toString())
            }

        })
    }
}
