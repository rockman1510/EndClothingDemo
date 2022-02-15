package com.endclothingdemo.product.mvi

import com.endclothingdemo.base.mvi.BaseIntent
import com.endclothingdemo.product.model.Product
import com.endclothingdemo.product.model.ProductType

sealed class ProductIntent : BaseIntent() {
    class FetchData(val type: ProductType = ProductType.BOTH) : ProductIntent()
}