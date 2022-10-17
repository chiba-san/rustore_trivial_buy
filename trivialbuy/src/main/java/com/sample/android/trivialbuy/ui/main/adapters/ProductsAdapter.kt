package com.sample.android.trivialbuy.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.android.trivialbuy.R
import com.sample.android.trivialbuy.databinding.ProductItemBinding

class ProductsAdapter(
    private val doOnBuy: (String) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductHolder>() {
    private var products: List<String> = emptyList()

    fun updateAdapter(products: List<String>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false))

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    inner class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ProductItemBinding.bind(view)

        fun bind(product: String) {

            binding.buy.setOnClickListener { doOnBuy.invoke(product) }
        }
    }
}
