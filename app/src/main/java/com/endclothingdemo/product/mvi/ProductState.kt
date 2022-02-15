package com.endclothingdemo.product.mvi

import com.endclothingdemo.base.mvi.BaseState
import com.endclothingdemo.product.model.Product

sealed class ProductState : BaseState() {
    object Loading : ProductState()
    class FetchData(val dataList: ArrayList<Product>) : ProductState()
    class Error(val message: String) : ProductState()
}