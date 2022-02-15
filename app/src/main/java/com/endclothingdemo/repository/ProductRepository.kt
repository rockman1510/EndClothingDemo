package com.endclothingdemo.repository

import com.endclothingdemo.product.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getTestProductApi(): Flow<ArrayList<Product>>
    suspend fun getProductApi(): Flow<ArrayList<Product>>
}