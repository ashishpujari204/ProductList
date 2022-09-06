package com.ashish.ashishproductlist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav")
data class FavClass(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "productId")
    var productId: Long,
    @ColumnInfo(name = "productName")
    var productName: String,
    @ColumnInfo(name = "productImage")
    var productImage: String,
    @ColumnInfo(name = "productPrice")
    var productPrice: String,
    @ColumnInfo(name = "productRating")
    var productRating: Int,
    @ColumnInfo(name = "isFav")
    var isFav: Boolean = false
)
