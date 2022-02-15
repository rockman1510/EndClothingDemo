package com.endclothingdemo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.endclothingdemo.MainCoroutineRule
import com.endclothingdemo.UnitTestUtils
import com.endclothingdemo.base.CoroutinesDispatcherProvider
import com.endclothingdemo.getOrAwaitValue
import com.endclothingdemo.product.model.ProductType
import com.endclothingdemo.product.mvi.ProductIntent
import com.endclothingdemo.product.mvi.ProductState
import com.endclothingdemo.product.viewmodel.ProductViewModel
import com.endclothingdemo.repository.FakeProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductViewModelTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

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
    fun fetchDataBoth1000ElementsTest() = mainCoroutineRule.dispatcher.runBlockingTest {
        val viewModel =
            createProductViewModel(
                UnitTestUtils.createFakeRepository(
                    UnitTestUtils.generateProducts(1000, ProductType.BOTH),
                    UnitTestUtils.generateProducts(1000, ProductType.BOTH),
                    dispatcherProvider
                )
            )
        viewModel.processIntent(ProductIntent.FetchData(ProductType.BOTH))
        val state = viewModel.getBaseState().getOrAwaitValue()
        assert(state is ProductState.FetchData)
        assert((state as ProductState.FetchData).dataList.size == 1000)
        for (i in state.dataList.indices) {
            if (i < 500) {
                assert(state.dataList[i].id == ProductType.HOODIES.type)
            } else {
                assert(state.dataList[i].id == ProductType.SNEAKERS.type)
            }
        }
    }

    @Test
    fun fetchDataHoodies1000ElementsTest() = mainCoroutineRule.dispatcher.runBlockingTest {
        val viewModel =
            createProductViewModel(
                UnitTestUtils.createFakeRepository(
                    UnitTestUtils.generateProducts(1000, ProductType.HOODIES),
                    UnitTestUtils.generateProducts(1000, ProductType.HOODIES),
                    dispatcherProvider
                )
            )
        viewModel.processIntent(ProductIntent.FetchData(ProductType.HOODIES))
        val state = viewModel.getBaseState().getOrAwaitValue()
        assert(state is ProductState.FetchData)
        assert((state as ProductState.FetchData).dataList.size == 1000)
        state.dataList.forEach {
            assert(it.id == ProductType.HOODIES.type)
            assert(it.id != ProductType.SNEAKERS.type)
        }
    }

    @Test
    fun fetchDataSneakers1000ElementsTest() = mainCoroutineRule.dispatcher.runBlockingTest {
        val viewModel =
            createProductViewModel(
                UnitTestUtils.createFakeRepository(
                    UnitTestUtils.generateProducts(1000, ProductType.SNEAKERS),
                    UnitTestUtils.generateProducts(1000, ProductType.SNEAKERS),
                    dispatcherProvider
                )
            )
        viewModel.processIntent(ProductIntent.FetchData(ProductType.SNEAKERS))
        val state = viewModel.getBaseState().getOrAwaitValue()
        assert(state is ProductState.FetchData)
        assert((state as ProductState.FetchData).dataList.size == 1000)
        state.dataList.forEach {
            assert(it.id != ProductType.HOODIES.type)
            assert(it.id == ProductType.SNEAKERS.type)
        }
    }

    @Test
    fun fetchDataHoodies500ElementsTest() = mainCoroutineRule.dispatcher.runBlockingTest {
        val viewModel =
            createProductViewModel(
                UnitTestUtils.createFakeRepository(
                    UnitTestUtils.generateProducts(1000, ProductType.BOTH),
                    UnitTestUtils.generateProducts(1000, ProductType.BOTH),
                    dispatcherProvider
                )
            )
        viewModel.processIntent(ProductIntent.FetchData(ProductType.HOODIES))
        val state = viewModel.getBaseState().getOrAwaitValue()
        assert(state is ProductState.FetchData)
        assert((state as ProductState.FetchData).dataList.size == 500)
        state.dataList.forEach {
            assert(it.id == ProductType.HOODIES.type)
            assert(it.id != ProductType.SNEAKERS.type)
        }
    }

    @Test
    fun fetchDataSneakers500ElementsTest() = mainCoroutineRule.dispatcher.runBlockingTest {
        val viewModel =
            createProductViewModel(
                UnitTestUtils.createFakeRepository(
                    UnitTestUtils.generateProducts(1000, ProductType.BOTH),
                    UnitTestUtils.generateProducts(1000, ProductType.BOTH),
                    dispatcherProvider
                )
            )
        viewModel.processIntent(ProductIntent.FetchData(ProductType.SNEAKERS))
        val state = viewModel.getBaseState().getOrAwaitValue()
        assert(state is ProductState.FetchData)
        assert((state as ProductState.FetchData).dataList.size == 500)
        state.dataList.forEach {
            assert(it.id != ProductType.HOODIES.type)
            assert(it.id == ProductType.SNEAKERS.type)
        }
    }

    @Test
    fun fetchDataNoneTest() = mainCoroutineRule.dispatcher.runBlockingTest {
        val viewModel =
            createProductViewModel(
                UnitTestUtils.createFakeRepository(
                    UnitTestUtils.generateProducts(1000, ProductType.BOTH),
                    UnitTestUtils.generateProducts(1000, ProductType.BOTH),
                    dispatcherProvider
                )
            )
        viewModel.processIntent(ProductIntent.FetchData(ProductType.NONE))
        assert(viewModel.getBaseState().getOrAwaitValue() is ProductState.Error)
        assert((viewModel.getBaseState().value as ProductState.Error).message == "No data!")
    }

    private fun createProductViewModel(repository: FakeProductRepository) = ProductViewModel(
        repository, dispatcherProvider
    )
}