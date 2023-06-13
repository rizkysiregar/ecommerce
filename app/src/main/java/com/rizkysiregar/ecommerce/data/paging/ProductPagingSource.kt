package com.rizkysiregar.ecommerce.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.data.network.api.ApiService
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import retrofit2.HttpException
import java.io.IOException

class ProductPagingSource(
    private val apiService: ApiService,
    private val query: QueryProductModel
) : PagingSource<Int, ItemsItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemsItem> {
        try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val size = 10
            val responseData = apiService.getProducts(
                query.search,
                query.brand,
                query.lowest,
                query.highest,
                query.sort,
                size,
                page
            )

            val item = responseData.data.items
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (page < responseData.data.totalPages) page + 1 else null

            return LoadResult.Page(item, prevKey, nextKey)
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException){
            return LoadResult.Error(e)
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ItemsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}