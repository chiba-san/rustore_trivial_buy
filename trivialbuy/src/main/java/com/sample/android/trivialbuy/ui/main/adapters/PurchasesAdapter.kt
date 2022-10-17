package com.sample.android.trivialbuy.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rustore.sdk.billingclient.model.purchase.Purchase
import com.sample.android.trivialbuy.R
import com.sample.android.trivialbuy.databinding.PurchaseItemBinding

class PurchasesAdapter(
    private val doOnConfirm: (String) -> Unit
) : RecyclerView.Adapter<PurchasesAdapter.PurchaseHolder>() {
    private var purchases: List<Purchase> = emptyList()

    fun updateAdapter(purchases: List<Purchase>) {
        this.purchases = purchases
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PurchaseHolder(LayoutInflater.from(parent.context).inflate(R.layout.purchase_item, parent, false))

    override fun onBindViewHolder(holder: PurchaseHolder, position: Int) {
        holder.bind(purchases[position])
    }

    override fun getItemCount() = purchases.size

    inner class PurchaseHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = PurchaseItemBinding.bind(view)

        fun bind(purchase: Purchase) {
            binding.textView1.text = purchase.purchaseId
            binding.textView2.text = purchase.productId
            binding.textView3.text = purchase.purchaseState.toString()
            binding.confirm.setOnClickListener {
                purchase.purchaseId?.let { doOnConfirm.invoke(it) }
            }
        }
    }
}
