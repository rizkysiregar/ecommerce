package com.rizkysiregar.ecommerce.ui.store

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.model.content.RoundedCorners
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.DummyStoreData
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import com.rizkysiregar.ecommerce.databinding.ItemContentGridBinding

class StoreAdapterGrid(
    private val data: List<ItemsItem>,
) :
    RecyclerView.Adapter<StoreAdapterGrid.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreAdapterGrid.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContentGridBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreAdapterGrid.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemContentGridBinding) :
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