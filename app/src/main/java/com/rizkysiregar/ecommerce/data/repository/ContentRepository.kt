package com.rizkysiregar.ecommerce.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rizkysiregar.ecommerce.data.local.db.EcommerceDao
import com.rizkysiregar.ecommerce.data.model.FulFillmentRequestModel
import com.rizkysiregar.ecommerce.data.model.NotificationEntity
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.data.model.RatingModel
import com.rizkysiregar.ecommerce.data.network.api.ApiService
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity
import com.rizkysiregar.ecommerce.data.network.response.DetailProductResponse
import com.rizkysiregar.ecommerce.data.network.response.FulFillmentResponse
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import com.rizkysiregar.ecommerce.data.network.response.PaymentResponse
import com.rizkysiregar.ecommerce.data.network.response.RatingResponse
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import com.rizkysiregar.ecommerce.data.network.response.ResponseReview
import com.rizkysiregar.ecommerce.data.network.response.SearchResponse
import com.rizkysiregar.ecommerce.data.network.response.TransactionResponse
import com.rizkysiregar.ecommerce.data.paging.ProductPagingSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(DelicateCoroutinesApi::class)
class ContentRepository(
    private val apiService: ApiService,
    private val ecommerceDao: EcommerceDao
) {
    fun getWishlist(): LiveData<List<DetailEntity>> {
        return ecommerceDao.getAllDataFromWishlist()
    }

    fun insertNewWishlist(data: DetailEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            ecommerceDao.insertNewWishlist(data)
        }
    }

    fun deleteWishlist(data: DetailEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            ecommerceDao.deleteWishlist(data)
        }
    }

    fun deleteCartEntity(cartEntity: CartEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            ecommerceDao.deleteCart(cartEntity)
        }
    }

    fun isProductExist(productId: String): LiveData<Boolean> {
        return ecommerceDao.isRecordExistsProductId(productId)
    }

    fun insertNewProductToCart(cartEntity: CartEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            ecommerceDao.insertToCart(cartEntity)
        }
    }

    fun getAllDataCart(): LiveData<List<CartEntity>> {
        return ecommerceDao.getAllCartProduct()
    }

    fun getProductCartThatSelected(): LiveData<List<CartEntity>> {
        return ecommerceDao.getCheckboxThatChecked()
    }

    suspend fun setProductCartSelected(cartEntity: CartEntity, state: Boolean) {
        cartEntity.isChecked = state
        ecommerceDao.updateIsProductSelected(cartEntity)
    }

    suspend fun setProductQuantity(cartEntity: CartEntity) {
        ecommerceDao.updateQuantityProduct(cartEntity)
    }

    suspend fun setReadNotification(notificationEntity: NotificationEntity, state: Boolean) {
        notificationEntity.isRead = state
        ecommerceDao.updateIsRead(notificationEntity)
    }

    fun getCountOfFalseValues(): LiveData<Int> {
        return ecommerceDao.getCountOfFalseValues()
    }

    fun getCountOfTrueValues(): LiveData<Int> {
        return ecommerceDao.getCountOfTrueValues()
    }

    fun getItemCountCart(): LiveData<Int> {
        return ecommerceDao.getItemCountCart()
    }

    fun getCountWishlist(): LiveData<Int> {
        return ecommerceDao.getCountItemWishlist()
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

    fun getPaymentMethod(
        onResponse: (Boolean, PaymentResponse?, throwable: String?) -> Unit
    ) {
        apiService.getPayment().enqueue(object : Callback<PaymentResponse> {
            override fun onResponse(
                call: Call<PaymentResponse>,
                response: Response<PaymentResponse>
            ) {
                val responseBody = response.body()
                onResponse(response.isSuccessful, responseBody, null)
            }

            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                onResponse(false, null, t.message.toString())
            }
        })
    }

    fun fulFillment(
        request: FulFillmentRequestModel,
        onResponse: (Boolean, FulFillmentResponse?, throwable: String?) -> Unit
    ) {
        apiService.postFullFil(request).enqueue(object : Callback<FulFillmentResponse> {
            override fun onResponse(
                call: Call<FulFillmentResponse>,
                response: Response<FulFillmentResponse>
            ) {
                val responseBody = response.body()
                onResponse(response.isSuccessful, responseBody, null)
            }

            override fun onFailure(call: Call<FulFillmentResponse>, t: Throwable) {
                onResponse(false, null, t.message.toString())
            }
        })
    }

    fun ratingProduct(
        request: RatingModel,
        onResponse: (Boolean, RatingResponse?, throwable: String?) -> Unit
    ) {
        apiService.postRating(request).enqueue(object : Callback<RatingResponse> {
            override fun onResponse(
                call: Call<RatingResponse>,
                response: Response<RatingResponse>
            ) {
                val responseBody = response.body()
                onResponse(response.isSuccessful, responseBody, null)
            }

            override fun onFailure(call: Call<RatingResponse>, t: Throwable) {
                onResponse(false, null, t.message.toString())
            }

        })
    }

    fun getTransactionList(): Flow<TransactionResponse> = flow {
        val response = apiService.getTransaction()
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        }else{
            // error handle
        }
    }

    // notification
    fun getAllNotification(): LiveData<List<NotificationEntity>> = ecommerceDao.getAllNotification()

    // log out

}
