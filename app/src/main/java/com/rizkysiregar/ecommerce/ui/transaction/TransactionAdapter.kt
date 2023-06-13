package com.rizkysiregar.ecommerce.ui.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.DataItemTransaction
import com.rizkysiregar.ecommerce.data.network.response.ItemsItemTransaction
import com.rizkysiregar.ecommerce.databinding.TransactionItemLayoutBinding


class TransactionAdapter(private val data: List<DataItemTransaction>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_item_layout, parent, false)
        )


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

     class ViewHolder(item: View) :
        RecyclerView.ViewHolder(item) {
        private val binding =  TransactionItemLayoutBinding.bind(item)

        fun bind(dataItem: DataItemTransaction) {
            binding.tvDateTransaction.text = dataItem.date

            Glide.with(itemView.context)
                .load(dataItem.image)
                .into(binding.imgTransaction)

            binding.tvTitleProductTranscation.text = dataItem.name
            binding.tvAmountProduct.text = "${dataItem.items[0].quantity} Barang"
            binding.tvTotalPriceTransaction.text = dataItem.total.toString()
        }
    }
}