package com.rizkysiregar.ecommerce.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rizkysiregar.ecommerce.data.model.NotificationEntity
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.DataDetail
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity

@Dao
interface EcommerceDao {
    // query get all wishlist from tbl_wishlist that is liked
    @Query("SELECT * FROM tbl_wishlist")
    fun getAllDataFromWishlist(): LiveData<List<DetailEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNewWishlist(data: DetailEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertToCart(cartEntity: CartEntity)

    @Query("SELECT * FROM tbl_cart")
    fun getAllCartProduct() : LiveData<List<CartEntity>>

    @Query("SELECT * FROM tbl_cart WHERE isChecked = 1")
    fun getCheckboxThatChecked(): LiveData<List<CartEntity>>

    @Update
    suspend fun updateIsProductSelected(cartEntity: CartEntity)
    @Update
    suspend fun updateQuantityProduct(cartEntity: CartEntity)

    @Update
    suspend fun updateIsRead(notificationEntity: NotificationEntity)

    @Query("SELECT EXISTS(SELECT * FROM tbl_wishlist WHERE productId = :productId)")
    fun isRecordExistsProductId(productId: String): LiveData<Boolean>

    // get boolean if all data isChecked in cart
    @Query("SELECT COUNT(*) FROM tbl_cart WHERE isChecked = 0")
    fun getCountOfFalseValues(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM tbl_cart")
    fun getItemCountCart(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM tbl_wishlist")
    fun getCountItemWishlist(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM tbl_cart")
    fun getTotalRowCount(): LiveData<Int>
    @Delete
    fun deleteWishlist(data: DetailEntity)

    @Delete
    fun deleteCart(cartEntity: CartEntity)

    // Notification
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNotification(notificationEntity: NotificationEntity)

    @Query("SELECT * FROM tbl_notification")
    fun getAllNotification(): LiveData<List<NotificationEntity>>

    @Query("DELETE FROM tbl_notification")
    fun clearTblNotification()

    @Query("DELETE FROM tbl_wishlist")
    fun clearTblWishlist()

    @Query("DELETE FROM tbl_cart")
    fun clearTblCart()
}