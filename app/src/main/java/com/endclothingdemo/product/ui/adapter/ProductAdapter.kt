package com.endclothingdemo.product.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.endclothingdemo.R
import com.endclothingdemo.databinding.LayoutProductItemBinding
import com.endclothingdemo.product.model.Product

class ProductAdapter(private val productSelectListener: ProductSelectListener) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var dataList = arrayListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutProductItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_product_item,
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position], productSelectListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: ArrayList<Product>) {
        dataList = newData
        notifyDataSetChanged()
    }

    fun getDataList() = dataList

    class ViewHolder(private val binding: LayoutProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, productSelect: ProductSelectListener) {
            binding.product = product
            binding.productSelectedListener = productSelect
        }
    }

    interface ProductSelectListener {
        fun onProductSelected(product: Product)
    }

}