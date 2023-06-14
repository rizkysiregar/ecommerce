package com.rizkysiregar.ecommerce.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbl_notification")
data class NotificationEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("type")
    val type: String,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("image")
    val image: String,

    @ColumnInfo("date")
    val date: String,

    @ColumnInfo("time")
    val time: String,

    @ColumnInfo("isRead")
    var isRead: Boolean = false
)