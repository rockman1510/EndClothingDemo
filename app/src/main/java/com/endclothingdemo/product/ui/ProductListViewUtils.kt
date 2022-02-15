package com.endclothingdemo.product.ui

import com.endclothingdemo.product.model.ProductType

class ProductListViewUtils {
    companion object {
        fun getProductType(isHoodies: Boolean, isSneakers: Boolean): ProductType {
            return if (isHoodies && isSneakers) {
                ProductType.BOTH
            } else if (isHoodies) {
                ProductType.HOODIES
            } else if (isSneakers) {
                ProductType.SNEAKERS
            } else {
                ProductType.NONE
            }
        }
    }
}