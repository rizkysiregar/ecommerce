package com.rizkysiregar.ecommerce.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rizkysiregar.ecommerce.data.model.NotificationEntity
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.DataDetail
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity

@Database(entities = [DetailEntity::class, CartEntity::class, NotificationEntity::class], version = 1, exportSchema = false)
abstract class EcommerceDatabase : RoomDatabase() {
    abstract fun ecommerceDao(): EcommerceDao
}