package com.endclothingdemo.product.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.endclothingdemo.R
import com.endclothingdemo.base.ui.BaseMVIFragment
import com.endclothingdemo.databinding.FragmentProductListBinding
import com.endclothingdemo.product.model.Product
import com.endclothingdemo.product.mvi.ProductIntent
import com.endclothingdemo.product.mvi.ProductState
import com.endclothingdemo.product.ui.ProductListViewUtils
import com.endclothingdemo.product.ui.adapter.ProductAdapter
import com.endclothingdemo.product.viewmodel.ProductViewModel
import com.endclothingdemo.utils.DialogUtils
import com.endclothingdemo.utils.SharePrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseMVIFragment<ProductIntent, ProductState>(),
    ProductAdapter.ProductSelectListener {

    override val viewModel: ProductViewModel by viewModels()

    private lateinit var binding: FragmentProductListBinding

    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product_list, container, false
        )
        binding.cbSneaker.isChecked = SharePrefUtils(requireContext()).isSneakers()
        binding.cbHoodies.isChecked = SharePrefUtils(requireContext()).isHoodies()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadProduct()
    }

    private fun initView() {
        binding.rvProduct.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        productAdapter = ProductAdapter(this)
        binding.adapter = productAdapter
        binding.cbHoodies.setOnClickListener { loadProduct() }
        binding.cbSneaker.setOnClickListener { loadProduct() }
        binding.refresher.setOnRefreshListener {
            loadProduct()
        }
    }

    private fun loadProduct() {
        val productType = ProductListViewUtils.getProductType(
            binding.cbHoodies.isChecked,
            binding.cbSneaker.isChecked
        )
        viewModel.processIntent(ProductIntent.FetchData(productType))
    }

    override fun onCallBackState(state: ProductState) {
        binding.refresher.isRefreshing = false
        when (state) {
            is ProductState.Loading -> {
                showLoadingDialog()
            }
            is ProductState.FetchData -> {
                hideLoadingDialog()
                productAdapter.updateData(state.dataList)
            }
            is ProductState.Error -> {
                productAdapter.updateData(arrayListOf())
                hideLoadingDialog()
                DialogUtils.messageDialog(
                    requireContext(), getString(R.string.error), state.message
                ).show()
            }
        }
    }

    override fun onStop() {
        SharePrefUtils(requireContext()).putIsHoodies(binding.cbHoodies.isChecked)
        SharePrefUtils(requireContext()).putIsSneakers(binding.cbSneaker.isChecked)
        super.onStop()
    }

    override fun onProductSelected(product: Product) {
        findNavController().navigate(ProductListFragmentDirections.gotoDetail(product))
    }
}