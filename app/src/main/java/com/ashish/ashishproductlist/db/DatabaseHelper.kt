package com.ashish.ashishproductlist.db

interface DatabaseHelper {

    suspend fun getFav(): List<FavClass>

    suspend fun getProduct(productId: Long): FavClass

    suspend fun getAllFav(): List<FavClass>

    suspend fun insertAll(favClass: FavClass)

    suspend fun insertOrUpdate(favClass: FavClass)

    suspend fun update(favClass: FavClass)

    suspend fun deleteFav(favClass: FavClass)

    suspend fun getRowCount(cityId: Long): Int
}