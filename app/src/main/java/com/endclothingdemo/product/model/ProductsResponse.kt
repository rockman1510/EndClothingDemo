package com.endclothingdemo.product.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @Expose
    @SerializedName("products")
    val products: ArrayList<Product>,
    @Expose
    @SerializedName("title")
    val title: String,
    @Expose
    @SerializedName("product_count")
    val product_count: Int
)