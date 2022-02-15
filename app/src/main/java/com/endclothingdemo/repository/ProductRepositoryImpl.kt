package com.endclothingdemo.repository

import com.endclothingdemo.api.ApiService
import com.endclothingdemo.base.CoroutinesDispatcherProvider
import com.endclothingdemo.product.model.Product
import com.endclothingdemo.product.model.ProductsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) :
    ProductRepository {

    override suspend fun getTestProductApi(): Flow<ArrayList<Product>> {
        return flow {
            emit((apiService.getTestProductApi().body() as ProductsResponse).products)
        }.flowOn(dispatcherProvider.IO)
    }

    override suspend fun getProductApi(): Flow<ArrayList<Product>> {
        return flow {
            emit((apiService.getProductApi().body() as ProductsResponse).products)
        }.flowOn(dispatcherProvider.IO)
    }
}