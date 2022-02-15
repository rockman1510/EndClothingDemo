package com.endclothingdemo.repository

import com.endclothingdemo.MainCoroutineRule
import com.endclothingdemo.UnitTestUtils
import com.endclothingdemo.base.CoroutinesDispatcherProvider
import com.endclothingdemo.product.model.Product
import com.endclothingdemo.product.model.ProductType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductRepositoryImplTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var dispatcherProvider: CoroutinesDispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = CoroutinesDispatcherProvider(
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher
        )
    }

    @Test
    fun getTestProductApi1000ElementTest() = mainCoroutineRule.dispatcher.runBlockingTest {
        var dataList = arrayListOf<Product>()
        fakeProductRepository = UnitTestUtils.createFakeRepository(
            UnitTestUtils.generateProducts(1000, ProductType.HOODIES),
            UnitTestUtils.generateProducts(1000, ProductType.HOODIES),
            dispatcherProvider
        )
        fakeProductRepository.getTestProductApi().collect { dataList = it }
        assert(dataList.size == 1000)
    }

    @Test
    fun getProductApi1000ElementTest() = mainCoroutineRule.dispatcher.runBlockingTest {
        var dataList = arrayListOf<Product>()
        fakeProductRepository = UnitTestUtils.createFakeRepository(
            UnitTestUtils.generateProducts(1000, ProductType.HOODIES),
            UnitTestUtils.generateProducts(1000, ProductType.HOODIES),
            dispatcherProvider
        )
        fakeProductRepository.getProductApi().collect { dataList = it }
        assert(dataList.size == 1000)
    }

    @Test
    fun getTestProductApiEmptyTest() = mainCoroutineRule.dispatcher.runBlockingTest {
        var dataList = arrayListOf<Product>()
        fakeProductRepository = UnitTestUtils.createFakeRepository(
            arrayListOf(), arrayListOf(), dispatcherProvider
        )
        fakeProductRepository.getTestProductApi().collect { dataList = it }
        assert(dataList.isNullOrEmpty())
    }

    @Test
    fun getProductApiEmptyTest() = mainCoroutineRule.dispatcher.runBlockingTest {
        var dataList = arrayListOf<Product>()
        fakeProductRepository = UnitTestUtils.createFakeRepository(
            arrayListOf(), arrayListOf(), dispatcherProvider
        )
        fakeProductRepository.getProductApi().collect { dataList = it }
        assert(dataList.isNullOrEmpty())
    }
}