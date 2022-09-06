package com.ashish.ashishproductlist.db

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getFav(): List<FavClass> {
        return appDatabase.favDao().getAll()
    }

    override suspend fun getProduct(productId : Long): FavClass {
        return appDatabase.favDao().getProduct(productId)
    }

    override suspend fun getAllFav(): List<FavClass> {
        return appDatabase.favDao().getAllFav()
    }

    override suspend fun insertAll(favClass: FavClass) {
        appDatabase.favDao().insertAll(favClass)
    }

    override suspend fun insertOrUpdate(favClass: FavClass) {
        appDatabase.favDao().insertOrUpdate(favClass)
    }

    override suspend fun update(favClass: FavClass) {
        appDatabase.favDao().update(favClass)
    }

    override suspend fun deleteFav(favClass: FavClass) {
        favClass.productId.let { appDatabase.favDao().delete(it) }
    }

    override suspend fun getRowCount(cityId: Long): Int {
        return appDatabase.favDao().getRowCount(cityId)
    }
}