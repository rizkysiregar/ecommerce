package com.rizkysiregar.ecommerce.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.DataDetail
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity

@Dao
interface EcommerceDao {
    // query get all wishlist from tbl_wishlist
    @Query("SELECT * FROM tbl_wishlist")
    fun getAllDataFromWishlist(): LiveData<List<DetailEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewWishlist(data: DetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToCart(cartEntity: CartEntity)

    @Query("SELECT * FROM tbl_cart")
    fun getAllCartProduct() : LiveData<List<CartEntity>>

    @Update
    fun updateWishlist(data: DetailEntity)

    @Delete
    fun deleteWishlist(data: DetailEntity)
}