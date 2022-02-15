package com.endclothingdemo.product.mvi

import com.endclothingdemo.base.mvi.BaseEffect
import com.endclothingdemo.product.model.Product

sealed class ProductEffect : BaseEffect() {
    class ShowProducts(val products: ArrayList<Product>) : ProductEffect()
    class NoProduct(val message: String): ProductEffect()
    object NoEffect : ProductEffect()
}