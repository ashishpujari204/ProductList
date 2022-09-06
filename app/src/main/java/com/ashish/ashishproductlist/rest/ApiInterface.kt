package com.ashish.ashishproductlist.rest

import com.ashish.ashishproductlist.model.Products
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

    @GET
    suspend fun getProducts(@Url url: String = "https://run.mocky.io/v3/2f06b453-8375-43cf-861a-06e95a951328"): Products
}