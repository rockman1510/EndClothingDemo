package com.endclothingdemo

import com.endclothingdemo.base.CoroutinesDispatcherProvider
import com.endclothingdemo.product.model.Product
import com.endclothingdemo.product.model.ProductType
import com.endclothingdemo.repository.FakeProductRepository

class UnitTestUtils {
    companion object {

        fun generateProducts(
            number: Int, type: ProductType = ProductType.BOTH
        ): ArrayList<Product> {
            val data = arrayListOf<Product>()
            for (i in 0 until number) {
                if (type == ProductType.BOTH) {
                    if (i % 2 == 0)
                        data.add(Product("1", "Sneaker", "$ 100", "Sneaker_$i"))
                    else
                        data.add(Product("2", "T-Shirt", "$ 100", "Hoodies_$i"))
                } else if (type == ProductType.SNEAKERS) {
                    data.add(Product("1", "Sneaker", "$ 100", "Sneaker_$i"))
                } else if (type == ProductType.HOODIES) {
                    data.add(Product("2", "T-Shirt", "$ 100", "Hoodies_$i"))
                }
            }
            return data
        }

        fun createFakeRepository(
            data1: ArrayList<Product>,
            data2: ArrayList<Product>,
            dispatcherProvider: CoroutinesDispatcherProvider
        ): FakeProductRepository {
            return FakeProductRepository(data1, data2, dispatcherProvider)
        }
    }
}