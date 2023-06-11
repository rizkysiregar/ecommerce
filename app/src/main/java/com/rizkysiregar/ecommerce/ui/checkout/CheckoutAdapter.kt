package com.rizkysiregar.ecommerce.ui.checkout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.databinding.CheckoutItemProductBinding

class CheckoutAdapter(
    private val selectedItems: List<CartEntity>
) : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.checkout_item_product, parent, false)
        )

    override fun getItemCount(): Int {
        return selectedItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = selectedItems[position]
        holder.bind(data)
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = CheckoutItemProductBinding.bind(item)

        fun bind(data: CartEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(imgProductCheckout)

                tvProductNameCheckout.text = data.productName
                tvVariantCheckout.text = data.variantName
                tvStockCheckout.text = "Stock: ${data.stock}"
                tvPriceCheckout.text = data.productPrice.toString()
            }
        }
    }

}