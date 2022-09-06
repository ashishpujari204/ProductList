package com.ashish.ashishproductlist.rest

open class RepositoryImplementation(private var apiInterface: ApiInterface
) {
    suspend fun getProducts() = apiInterface.getProducts()
}