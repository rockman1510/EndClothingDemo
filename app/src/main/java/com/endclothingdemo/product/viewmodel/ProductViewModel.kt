package com.endclothingdemo.product.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.endclothingdemo.base.CoroutinesDispatcherProvider
import com.endclothingdemo.base.mvi.BaseViewModel
import com.endclothingdemo.product.model.Product
import com.endclothingdemo.product.model.ProductType
import com.endclothingdemo.product.mvi.ProductIntent
import com.endclothingdemo.product.mvi.ProductState
import com.endclothingdemo.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) :
    BaseViewModel<ProductIntent, ProductState>() {

    private val TAG = ProductViewModel::class.java.simpleName

    override val baseState: MutableLiveData<ProductState> = MutableLiveData()

    private var productList = listOf<Product>()

    override fun processIntent(intent: ProductIntent) {
        viewModelScope.launch(dispatcherProvider.Main) {
            baseState.postValue(ProductState.Loading)
        }
        when (intent) {
            is ProductIntent.FetchData -> {
                fetchData(intent)
            }
        }
    }

    private fun fetchData(intent: ProductIntent.FetchData) {
        viewModelScope.launch(dispatcherProvider.Main) {
            if (intent.type != ProductType.NONE) {
                baseState.postValue(ProductState.FetchData(arrayListOf()))
                try {
                    productRepository.getTestProductApi()
                        .collect { productList = it }
                    if (!productList.isNullOrEmpty()) {
                        productList = withContext(dispatcherProvider.Default) {
                            productList.sortedByDescending { it.id }
                        }
                        baseState.postValue(generateData(ArrayList(productList), intent.type))
                    } else {
                        baseState.postValue(ProductState.Error("No data!"))
                    }
                } catch (e: Exception) {
                    baseState.postValue(ProductState.Error(e.message.toString()))
                }
            } else {
                baseState.postValue(ProductState.Error("No data!"))
            }
        }
    }

    private suspend fun generateData(data: ArrayList<Product>, type: ProductType): ProductState {
        val newData = if (type == ProductType.BOTH) {
            data
        } else {
            withContext(dispatcherProvider.Default) {
                data.filter { it.id == type.type } as ArrayList<Product>
            }
        }
        return ProductState.FetchData(newData)
    }
}