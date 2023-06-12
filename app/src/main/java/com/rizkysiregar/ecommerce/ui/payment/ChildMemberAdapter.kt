package com.rizkysiregar.ecommerce.ui.payment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.ItemsPayment
import com.rizkysiregar.ecommerce.databinding.ItemInnerPaymentLayoutBinding
import com.rizkysiregar.ecommerce.ui.cart.CartAdapter

class ChildMemberAdapter(
    val data: List<ItemsPayment>,
    private val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<ChildMemberAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_inner_payment_layout, parent, false)
        )

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataItem = data[position]
        holder.bind(dataItem)
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemInnerPaymentLayoutBinding.bind(itemView)
        fun bind(dataItem: ItemsPayment) {
            Glide.with(itemView.context)
                .load(dataItem.image)
                .into(binding.imgItemPayment)

            binding.tvTitleItemPayment.text = dataItem.label
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }
}