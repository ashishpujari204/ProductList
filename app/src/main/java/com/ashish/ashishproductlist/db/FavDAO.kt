package com.ashish.ashishproductlist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavDAO {

    @Query("SELECT * FROM fav")
    suspend fun getAll(): List<FavClass>

    @Query("SELECT * FROM fav where isFav = '1'")
    suspend fun getAllFav(): List<FavClass>

    @Query("SELECT * FROM fav where productId = :productId")
    suspend fun checkProductID(productId: Long): List<FavClass>

    @Insert
    suspend fun insertAll(favClass: FavClass)

    @Query("DELETE FROM fav where productId = :productId")
    suspend fun delete(productId: Long): Int

    @Query("Select * FROM fav where productId = :productId")
    suspend fun getProduct(productId: Long): FavClass

    @Query("Update fav set isFav =:isFav  where productId = :productId")
    suspend fun updateProduct(productId: Long, isFav: Boolean)

    @Query("SELECT count(*) from fav where productId = :productId")
    fun getRowCount(productId: Long): Int

    suspend fun insertOrUpdate(favClass: FavClass) {
        if (checkProductID(favClass.productId).isEmpty()) {
            insertAll(favClass)
        }
    }

    suspend fun update(favClass: FavClass) {
        if (checkProductID(favClass.productId).isNotEmpty()) {
            updateProduct(favClass.productId, favClass.isFav)
        }
    }
}