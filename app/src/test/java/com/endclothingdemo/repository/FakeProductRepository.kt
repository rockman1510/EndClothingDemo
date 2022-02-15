package com.endclothingdemo.repository

import android.annotation.SuppressLint
import com.endclothingdemo.base.CoroutinesDispatcherProvider
import com.endclothingdemo.product.model.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeProductRepository(
    private val data1: ArrayList<Product>,
    private val data2: ArrayList<Product>,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ProductRepository {

    @SuppressLint("CheckResult")
    override suspend fun getTestProductApi(): Flow<ArrayList<Product>> {
        return flow {
            emit(data1)
        }.flowOn(coroutinesDispatcherProvider.IO)
    }

    override suspend fun getProductApi(): Flow<ArrayList<Product>> {
        return flow {
            emit(data2)
        }.flowOn(coroutinesDispatcherProvider.IO)
    }
}