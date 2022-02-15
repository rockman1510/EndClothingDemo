package com.endclothingdemo.api

import com.endclothingdemo.product.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/media/catalog/android_test_example.json")
    suspend fun getTestProductApi(): Response<ProductsResponse>

    @GET("/media/catalog/example.json")
    suspend fun getProductApi(): Response<ProductsResponse>
}