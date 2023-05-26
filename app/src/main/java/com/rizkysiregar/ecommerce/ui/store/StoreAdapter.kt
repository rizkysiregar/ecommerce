package com.rizkysiregar.ecommerce.ui.store

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.DummyStoreData
import com.rizkysiregar.ecommerce.data.network.response.DataProduct
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import com.rizkysiregar.ecommerce.databinding.ContentItemLinearBinding

class StoreAdapter(
    private val data: List<ItemsItem>,
) :
    RecyclerView.Adapter<StoreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContentItemLinearBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ContentItemLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemsItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(R.drawable.thumbnail)
                    .into(imgItem)

                tvTitleContent.text = data.productName
                tvSeller.text = data.store
                textPrice.text = data.productPrice.toString()
                tvRatingContent.text = "${data.productRating} | ${data.sale}"
            }
        }
    }

}