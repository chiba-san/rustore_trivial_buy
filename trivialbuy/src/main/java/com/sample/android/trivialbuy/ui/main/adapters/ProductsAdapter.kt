package com.sample.android.trivialbuy.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rustore.sdk.billingclient.model.product.Product
import com.sample.android.trivialbuy.R
import com.sample.android.trivialbuy.databinding.ProductItemBinding

class ProductsAdapter(
    private val doOnBuy: (String) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductHolder>() {
    private var products: List<Product> = emptyList()

    fun updateAdapter(products: List<Product>) {
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

        fun bind(product: Product) {
            binding.textView1.text = product.productId
            binding.textView2.text = product.priceLabel
            binding.textView3.text = product.productStatus.toString()
            binding.buy.setOnClickListener { doOnBuy.invoke(product.productId) }
        }
    }
}
